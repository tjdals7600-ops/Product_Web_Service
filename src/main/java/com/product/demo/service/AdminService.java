package com.product.demo.service;

import com.product.demo.config.PasswordEncoder;
import com.product.demo.dto.AdminCreateRequest;
import com.product.demo.dto.AdminResponse;
import com.product.demo.dto.AdminUpdateRequest;
import com.product.demo.dto.LoginRequest;
import com.product.demo.entity.Admin;
import com.product.demo.repository.AdminRepository;
import com.product.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    //속성
    private final AdminRepository adminRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;


    // 관리자 생성(회원 가입)
    @Transactional
    // 0.컨트롤러에서 데이터 받기
    public AdminResponse createAdmin(AdminCreateRequest request) {

        // 1.데이터 준비하기
        String newUsername = request.getUsername();
        String newEmail = request.getEmail();
        String newPassword = request.getPassword();

        // 1-2. 이메일 중복 확인하기
        boolean existsEmail = adminRepository.existsByEmail(newEmail);
        if (existsEmail) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 1-3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(newPassword);


        // 2.저장할 엔티티 만들기
        Admin newAdmin = new Admin(newUsername, newEmail, encodedPassword);

        // 3,4.db에 admin 저장, 저장한 결과 받기
        Admin savedAdmin = adminRepository.save(newAdmin);

        // 5. 저장된 데이터 준비
        Long savedAdminId = savedAdmin.getId();
        String savedAdminUserName = savedAdmin.getUsername();
        String savedAdminEmail = savedAdmin.getEmail();

        // 6. 응답 dto 만들기
        AdminResponse adminResponse = new AdminResponse(savedAdminId, savedAdminUserName, savedAdminEmail);

        // 7. 반환하기
        return adminResponse;


    }

    // 관리자 단건 조회
    @Transactional(readOnly = true)
    // 0. 컨트롤러에서 데이터 받기
    public AdminResponse getDetailAdmin(Long id) {
        // 1.db에서 id로 관리자 조회
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("관리자를 찾을 수 없습니다."));

        //2. 데이터 준비
        Long responseId = admin.getId();
        String responseUsername = admin.getUsername();
        String responseEmail = admin.getEmail();

        //3. 응답 dto 만들기
        AdminResponse response = new AdminResponse(
                responseId,
                responseUsername,
                responseEmail
        );

        //4. 반환하기
        return response;
    }

    // 관리자 전체 조회
    @Transactional(readOnly = true)
    public List<AdminResponse> getAllAdminList() {

        // 1.관리자 목록 조회하기
        List<Admin> findAdminList = adminRepository.findAll();

        // 2. dto 만들기 stream 사용
        List<AdminResponse> adminResponseList = findAdminList.stream()
                .map(admin -> new AdminResponse(admin.getId(), admin.getUsername(), admin.getEmail()))
                .collect(Collectors.toList());
        // 3. 반환하기
        return adminResponseList;
    }

    // 관리자 수정
    @Transactional
    //1. 컨트롤러에서 데이터 받기
    public AdminResponse updateAdmin(Long adminId, AdminUpdateRequest request) {
        // 2. db에서 관리자 조회
        Admin findAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("관리자를 찾을 수 없습니다,"));

        // 3. 데이터 수정
        String updateUsername = request.getUsername();
        String updateEmail = request.getEmail();


        // 4. 수정한 객체 반환
        Admin updateAdmin = findAdmin;

        // 5. 데이터 준비
        Long responseId = updateAdmin.getId();
        String responseUsername = updateAdmin.getUsername();
        String responseEmail = updateAdmin.getEmail();

        // 6. 응답 dto
        AdminResponse response = new AdminResponse(
                responseId,
                responseUsername,
                responseEmail
        );

        // 7 반환
        return response;
    }
    // 관리자 삭제
    @Transactional
    public void deleteAdmin(Long adminId) {
        // 1. db에서 관리자 조회
        Admin fingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("관리자를 찾을 수 없습니다"));
        // 1-1. 해당 관리자가 상품 등록 했는지 확인
        boolean existProduct = productRepository.existsByAdmin(fingAdmin);

        // 없을 경우 예외 처리
        if (existProduct) {
            throw new IllegalArgumentException("상품 등록 이력이 있는 관리자는 삭제할 수 없습니다.");
        }

        // 2.데이터 삭제
        adminRepository.delete(fingAdmin);
    }

    // 관리자 로그인
    @Transactional
    public void login(LoginRequest request, HttpSession session) {
        // 1. 관리자 조회
        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자 입니다."));

        // 2. 비밀번호 검증하기
        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                admin.getPassword()
        );
        if (!matches) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 세션에 정보 저장하기
        session.setAttribute("LOGIN_ADMIN_ID", admin.getId());
    }

    // 관리자 로그아웃
    @Transactional
    public void logout(HttpSession session) {
        // 세션 삭제
        session.invalidate();
    }
}



