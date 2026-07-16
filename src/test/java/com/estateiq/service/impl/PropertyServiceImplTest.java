package com.estateiq.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.estateiq.exception.PropertyNotFoundException;
import com.estateiq.repository.PropertyRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @Test
    void getPropertyByIdShouldThrow404WhenMissing() {
        when(propertyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PropertyNotFoundException.class, () -> propertyService.getPropertyById(99L));
    }
}