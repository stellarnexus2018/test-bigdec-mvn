package ru.master.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class FormulaeServiceTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getMwrYield() {
    FormulaeService fs = new FormulaeService();

    //    BigDecimal compared_result = BigDecimal.valueOf(4998569345.37605579D);
    BigDecimal compared_result = BigDecimal.valueOf(499856934537605579L, 8);

    Long _urgency = 5L;
    BigDecimal _z_factor = BigDecimal.valueOf(0.00533956);
    LocalDate _calc_date = LocalDate.of(2021, 3, 4); //2021-03-04;
    LocalDate _insurance_start_date = LocalDate.of(2020, 4, 24); // 2020-04-24
    BigDecimal _guarantee_rate = BigDecimal.valueOf(100.00000000);

    BigDecimal _insurance_prem_rub = BigDecimal.valueOf(200000.0000);
//    BigDecimal _insurance_prem_rub = null;

    BigDecimal _insurance_prem_nonrub = BigDecimal.valueOf(0.0000);

    BigDecimal _profitability = BigDecimal.valueOf(21493.8481);

    log.info("простое инфо");
    log.error("простая ошибка");




    BigDecimal result = fs.getMwrYield("prot_uuid",
        _urgency,
        _z_factor,
        _calc_date,
        _insurance_start_date,
        _guarantee_rate,
        _insurance_prem_rub,
        _insurance_prem_nonrub,
        _profitability,
        1L);
    //isEqualsBigDecimal
    System.out.println("result1: " + result);
    System.out.println("result2: " + compared_result);
//    Assertions.assertTrue((result.compareTo(compared_result)) == 0);
    Assertions.assertEquals(result, compared_result);
  }
}