package com.innowisegroup.simpleblog.service;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserLastnameValidationException;
import com.innowisegroup.simpleblog.model.User;
import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.repository.UserRepository;
import com.innowisegroup.simpleblog.service.mapping.UserMappingService;
import com.innowisegroup.simpleblog.service.mapping.UserMappingServiceImpl;
import com.innowisegroup.simpleblog.service.validation.UserValidationService;
import com.innowisegroup.simpleblog.service.validation.UserValidationServiceImpl;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private final UserMappingService userMappingServiceSpy = new UserMappingServiceImpl();

    @Spy
    private final UserValidationService userValidationService = new UserValidationServiceImpl();

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userMappingServiceSpy, userValidationService);
    }

    /**
     * {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    public void testUsersSortWhileGetAllUsers() {
        fillSortTestUserList();
        fakeSortUserDtoList();
        Mockito.when(userRepository.findAll()).thenReturn(USER_LIST);
        Mockito.when(userMappingServiceSpy.convertToDto(USER_LIST.get(0))).thenReturn(USER_DTO_LIST.get(0));
        Mockito.when(userMappingServiceSpy.convertToDto(USER_LIST.get(1))).thenReturn(USER_DTO_LIST.get(1));
        Mockito.when(userMappingServiceSpy.convertToDto(USER_LIST.get(2))).thenReturn(USER_DTO_LIST.get(2));
        Mockito.when(userMappingServiceSpy.convertToDto(USER_LIST.get(3))).thenReturn(USER_DTO_LIST.get(3));
        List<UserDto> sorted = userService.getAllUsers();
        MatcherAssert.assertThat(sorted, Matchers.contains(
                USER_DTO_LIST.get(0),
                USER_DTO_LIST.get(1),
                USER_DTO_LIST.get(2),
                USER_DTO_LIST.get(3)
        ));
    }

    /**
     * {@link UserServiceImpl#getUsersWithCapitalizedNames()}
     */
    @Test
    public void testUserNameCapitalizingSmallFirstLetter() {
        User user = new User();
        user.setName("name");

        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<UserDto> result = userService.getUsersWithCapitalizedNames();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Name", result.get(0).getName());
    }

    /**
     * {@link UserServiceImpl#getUsersWithCapitalizedNames()}
     */
    @Test
    public void testUserNameCapitalizingBigFirstLetter() {
        User user = new User();
        user.setName("Name");

        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<UserDto> result = userService.getUsersWithCapitalizedNames();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Name", result.get(0).getName());
    }

    /**
     * {@link UserServiceImpl#getUsersWithCapitalizedNames()}
     */
    @Test
    public void testUserNameCapitalizingFirstIsNotLetter() {
        User user = new User();
        user.setName("$ame");

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDto> result = userService.getUsersWithCapitalizedNames();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("$ame", result.get(0).getName());
    }

    /**
     * {@link UserServiceImpl#getSortedByNameUsersByRole}
     */
    @Test
    public void testResultUserListContainsSpecifiedRoleOnly() {
        fillRoleFilteringTestUserList();
        userListConvertToUserDtoList();

//        Mockito.when(userRepository.getAllUserEntities()).thenReturn(USER_LIST);
        List<UserDto> result = userService.getSortedByNameUsersByRole(UserRole.valueOfLabel("aDmiN"));

        Assertions.assertNotNull(result);
        Assertions.assertTrue(
                result.stream()
                        .allMatch(userDto -> UserServiceTest.checkUserRole(userDto, UserRole.ADMIN)));
        Mockito.verify(
                userMappingServiceSpy,
                Mockito.times(USER_LIST.size() + result.size())
        )
                .convertToDto(any());
    }

    /**
     * {@link UserValidationServiceImpl#validate(UserDto)}
     */
    @Test
    public void testUserLastnameValidationExceptionIsThrownWhenLastnameIsNull() {
        Assertions.assertThrows(UserLastnameValidationException.class,
                () -> userValidationService.validate(new UserDto()));
    }

    /**
     * {@link UserValidationServiceImpl#validate(UserDto)}
     */
    @Test
    public void testUserLastnameValidationExceptionIsNotThrownWhenLastnameIsValid() {
        Assertions.assertDoesNotThrow(() -> {
            UserDto userDto = new UserDto();
            userDto.setLastname("Name");
            userValidationService.validate(userDto);
        });
    }

    /**
     * {@link UserValidationServiceImpl#validate(UserDto)}
     */
    @Test
    public void testUserLastnameValidationExceptionIsNotThrownWhenLastnameContainsEmptyCharacters() {
        Assertions.assertDoesNotThrow(() -> {
            UserDto userDto = new UserDto();
            userDto.setLastname(" Name\n");
            userValidationService.validate(userDto);
        });
    }

    /**
     * {@link UserValidationServiceImpl#validate(UserDto)}
     */
    @Test
    public void testUserLastnameValidationExceptionIsThrownWhenLastnameIsInvalid() {
        Assertions.assertThrows(UserLastnameValidationException.class,
                () -> {
                    UserDto userDto = new UserDto();
                    userDto.setLastname(" \n");
                    userValidationService.validate(userDto);
                });
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
                .map(userMappingServiceSpy::convertToDto)
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
                .map(userMappingServiceSpy::convertToDto)
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
