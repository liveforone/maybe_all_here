package maybe_all_here.userservice.service.util;

import maybe_all_here.userservice.domain.Member;
import maybe_all_here.userservice.dto.mileage.MileageResponse;
import maybe_all_here.userservice.dto.response.MemberInfoResponse;
import maybe_all_here.userservice.dto.response.MemberResponse;
import maybe_all_here.userservice.dto.signupAndLogin.MemberSignupRequest;
import maybe_all_here.userservice.utility.CommonUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MemberMapper {

    public static MemberResponse entityToDto(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .realName(member.getRealName())
                .auth(member.getAuth())
                .build();
    }

    public static List<MemberResponse> entityToDtoList(List<Member> members) {
        return members
                .stream()
                .map(MemberMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public static MemberInfoResponse createMemberInfo(
            Member member,
            MileageResponse mileageResponse
    ) {
        return MemberInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .realName(member.getRealName())
                .auth(member.getAuth())
                .mileagePoint((CommonUtils.isNull(mileageResponse) ? 0 : mileageResponse.getMileagePoint()))
                .build();
    }

    public static Member dtoToEntity(MemberSignupRequest memberSignupRequest) {
        return Member.builder()
                .id(memberSignupRequest.getId())
                .email(memberSignupRequest.getEmail())
                .password(memberSignupRequest.getPassword())
                .realName(memberSignupRequest.getRealName())
                .auth(memberSignupRequest.getAuth())
                .build();
    }
}
