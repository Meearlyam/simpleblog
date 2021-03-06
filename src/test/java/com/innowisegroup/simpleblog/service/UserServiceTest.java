package com.innowisegroup.simpleblog.service;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.model.User;
import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.repository.UserRepository;
import com.innowisegroup.simpleblog.service.mapping.UserMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

/**
 * {@link UserServiceImpl}
 */
@ExtendWith({
//        SpringExtension.class,
        MockitoExtension.class
})
//@ContextConfiguration(classes = { WebConfig.class })
//@WebAppConfiguration
public class UserServiceTest {
    private static List<User> USER_LIST;
    private static List<UserDto> USER_DTO_LIST;

    @Mock
    private UserRepository userRepository;

    @Spy
    private final UserMapper userMapperSpy = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userMapperSpy);
    }

    /**
     * {@link UserServiceImpl#findAll()}
     */
    @Test
    public void testUsersSortWhileGetAllUsers() {
        fillSortTestUserList();
        fakeSortUserDtoList();
        Mockito.when(userRepository.findAll()).thenReturn(USER_LIST);
        Mockito.when(userMapperSpy.convertToDto(USER_LIST.get(0))).thenReturn(USER_DTO_LIST.get(0));
        Mockito.when(userMapperSpy.convertToDto(USER_LIST.get(1))).thenReturn(USER_DTO_LIST.get(1));
        Mockito.when(userMapperSpy.convertToDto(USER_LIST.get(2))).thenReturn(USER_DTO_LIST.get(2));
        Mockito.when(userMapperSpy.convertToDto(USER_LIST.get(3))).thenReturn(USER_DTO_LIST.get(3));
        List<UserDto> sorted = userService.findAll();
        MatcherAssert.assertThat(sorted, Matchers.contains(
                USER_DTO_LIST.get(0),
                USER_DTO_LIST.get(1),
                USER_DTO_LIST.get(2),
                USER_DTO_LIST.get(3)
        ));
    }

    /**
     * {@link UserServiceImpl#findAllWithCapitalizedNames()}
     */
    @Test
    public void testUserNameCapitalizingSmallFirstLetter() {
        User user = new User();
        user.setName("name");

        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<UserDto> result = userService.findAllWithCapitalizedNames();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Name", result.get(0).getName());
    }

    /**
     * {@link UserServiceImpl#findAllWithCapitalizedNames()}
     */
    @Test
    public void testUserNameCapitalizingBigFirstLetter() {
        User user = new User();
        user.setName("Name");

        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<UserDto> result = userService.findAllWithCapitalizedNames();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Name", result.get(0).getName());
    }

    /**
     * {@link UserServiceImpl#findAllWithCapitalizedNames()}
     */
    @Test
    public void testUserNameCapitalizingFirstIsNotLetter() {
        User user = new User();
        user.setName("$ame");

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDto> result = userService.findAllWithCapitalizedNames();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("$ame", result.get(0).getName());
    }

    /**
     * {@link UserServiceImpl#findSortedByPasswordUsersByRole}
     */
    @Test
    public void testResultUserListContainsSpecifiedRoleOnly() {
        fillRoleFilteringTestUserList();
        userListConvertToUserDtoList();

//        Mockito.when(userRepository.getAllUserEntities()).thenReturn(USER_LIST);
        List<UserDto> result = userService.findSortedByPasswordUsersByRole(UserRole.valueOfLabel("aDmiN"));

        Assertions.assertNotNull(result);
        Assertions.assertTrue(
                result.stream()
                        .allMatch(userDto -> UserServiceTest.checkUserRole(userDto, UserRole.ADMIN)));
        Mockito.verify(
                userMapperSpy,
                Mockito.times(USER_LIST.size() + result.size())
        )
                .convertToDto(any());
    }

    private static boolean checkUserRole(UserDto userDto, UserRole role) {
        return userDto.getRole().equals(role);
    }

    private static void fillSortTestUserList() {
        final String NAME = "Vera";
        final String LAST_NAME = "Shavela";
        USER_LIST = new ArrayList<>();

        USER_LIST.add(new User());
        User user2 = new User();
        user2.setName(NAME);
        USER_LIST.add(user2);
        User user3 = new User();
        user3.setName(NAME);
        user3.setLastname(LAST_NAME);
        USER_LIST.add(user3);
        User user4 = new User();
        user4.setLastname(LAST_NAME);
        USER_LIST.add(user4);
    }

    private void fakeSortUserDtoList() {
        USER_DTO_LIST = new ArrayList<>();
        List<UserDto> unsortedUsersDtos = USER_LIST
                .stream()
                .map(userMapperSpy::convertToDto)
                .collect(Collectors.toList());

        UserDto uDto1 = unsortedUsersDtos.get(0);
        UserDto uDto2 = unsortedUsersDtos.get(1);
        UserDto uDto3 = unsortedUsersDtos.get(2);
        UserDto uDto4 = unsortedUsersDtos.get(3);

        USER_DTO_LIST.add(uDto3);
        USER_DTO_LIST.add(uDto2);
        USER_DTO_LIST.add(uDto4);
        USER_DTO_LIST.add(uDto1);
    }

    public void userListConvertToUserDtoList() {
        USER_DTO_LIST = USER_LIST
                .stream()
                .map(userMapperSpy::convertToDto)
                .collect(Collectors.toList());
    }

    public void fillRoleFilteringTestUserList() {
        USER_LIST = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            User user = new User();
            user.setRole(UserRole.ADMIN);
            user.setPassword(
                    String.format("admin%d", i)
            );
            USER_LIST.add(user);
        }
        for(int i = 0; i < 3; i++) {
            User user = new User();
            user.setRole(UserRole.AUTHOR);
            user.setPassword(
                    String.format("author%d", i)
            );
            USER_LIST.add(user);
        }
        for(int i = 0; i < 3; i++) {
            User user = new User();
            user.setRole(UserRole.SUBSCRIBER);
            user.setPassword(
                    String.format("subscriber%d", i)
            );
            USER_LIST.add(user);
        }
    }

}
