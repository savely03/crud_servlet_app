//package com.github.savely03.crudservletapp.service.impl;
//
//import com.github.javafaker.Faker;
//import com.github.savely03.crudservletapp.dto.CarDto;
//import com.github.savely03.crudservletapp.mapper.CarMapper;
//import com.github.savely03.crudservletapp.model.Car;
//import com.github.savely03.crudservletapp.repository.CarRepository;
//import com.github.savely03.crudservletapp.service.CarService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.MockedStatic;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.stream.LongStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CarServiceImplTest {
//    private static CarRepository carRepository;
//    private static CarService out;
//    private final Faker faker = new Faker();
//    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);
//
//    @BeforeAll
//    static void beforeAll() {
//        try (MockedStatic<CarRepository> carRepositoryMocked = mockStatic(CarRepository.class)) {
//            carRepository = mock(CarRepository.class);
//            carRepositoryMocked.when(CarRepository::getInstance).thenReturn(carRepository);
//            out = CarServiceImpl.getInstance();
//        }
//    }
//
//    @BeforeEach
//    void setUp() {
//        reset(carRepository);
//    }
//
//    @Test
//    void findAllTest() {
//        List<Car> cars = generateCars();
//        List<CarDto> carsDto = cars.stream()
//                .map(carMapper::toDto)
//                .toList();
//
//        when(carRepository.findAll()).thenReturn(cars);
//
//        assertThat(out.findAll()).isEqualTo(carsDto);
//        assertThat(out.findAll()).hasSize(cars.size());
//
//    }
//
//    @Test
//    void findByIdTest() {
//        Car car = generateCar();
//        CarDto carDto = carMapper.toDto(car);
//
//        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
//
//        assertThat(out.findById(car.getId())).isEqualTo(carDto);
//    }
//
//    @Test
//    void findByIdTestWhenCarNotFound() {
//        assertThatExceptionOfType(CarNotFoundException.class).isThrownBy(
//                () -> out.findById(1L)
//        );
//    }
//
//
//    @Test
//    void saveTest() {
//        Car car = generateCar();
//        CarDto carDto = carMapper.toDto(car);
//
//        when(carRepository.save(car)).thenReturn(car);
//
//        assertThat(out.save(carDto)).isEqualTo(carDto);
//    }
//
//    @Test
//    void saveWhenIncorrectValidationTest() {
//        CarDto carDto = CarDto.builder().build();
//
//        assertThatExceptionOfType(CarValidationException.class).isThrownBy(
//                () -> out.save(carDto)
//        );
//    }
//
//    @Test
//    void updateTest() {
//        Car car = generateCar();
//        CarDto carDto = carMapper.toDto(car);
//
//        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
//        when(carRepository.update(car)).thenReturn(car);
//
//        assertThat(out.update(carDto.getId(), carDto)).isEqualTo(carDto);
//
//    }
//
//    @Test
//    void updateWhenCarNotFoundTest() {
//        Car car = generateCar();
//        CarDto carDto = carMapper.toDto(car);
//
//        assertThatExceptionOfType(CarNotFoundException.class).isThrownBy(
//                () -> out.update(carDto.getId(), carDto)
//        );
//
//    }
//
//    @Test
//    void updateWhenIncorrectValidationTest() {
//        CarDto carDto = CarDto.builder().build();
//
//        assertThatExceptionOfType(CarValidationException.class).isThrownBy(
//                () -> out.update(carDto.getId(), carDto)
//        );
//    }
//
//    @Test
//    void deleteByIdTest() {
//        Long id = ThreadLocalRandom.current().nextLong(1, 1000);
//
//        when(carRepository.deleteById(id)).thenReturn(true);
//
//        out.deleteById(id);
//
//        verify(carRepository).deleteById(id);
//
//    }
//
//    @Test
//    void deleteByIdWenCarNotFoundTest() {
//        assertThatExceptionOfType(CarNotFoundException.class).isThrownBy(
//                () -> out.deleteById(1L)
//        );
//
//    }
//
//    @Test
//    void getCountCars() {
//        String color = faker.color().name();
//        int count = faker.random().nextInt(1, 100);
//
//        when(carRepository.getCountCars(color)).thenReturn(count);
//
//        assertThat(out.getCountCars(color)).isEqualTo(count);
//
//    }
//
//    private Car generateCar() {
//        return Car.builder()
//                .id(1L)
//                .engineCapacity(faker.random().nextDouble())
//                .releaseDate(LocalDate.now())
//                .price(BigDecimal.TEN)
//                .model(faker.name().name())
//                .color(faker.color().name())
//                .build();
//    }
//
//    private List<Car> generateCars(int count) {
//        return LongStream.iterate(1, i -> i + 1)
//                .limit(count)
//                .mapToObj(i -> {
//                    Car car = generateCar();
//                    car.setId(i);
//                    return car;
//                })
//                .toList();
//    }
//
//    private List<Car> generateCars() {
//        return generateCars(10);
//    }
//}