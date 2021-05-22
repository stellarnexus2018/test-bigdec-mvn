package ru.master.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class Utils {
  static final String[] checked_status = {"Действует", "Проект", "Завершен", "Завершён"};

  public static String GetQuotedString(String _s){
    return String.format("'%s'", _s.trim());
  }
  public static String GetLongAsString(long _l){
    return String.format("%d",  _l);
  }

  /**
   * Вычисление признака равенства проверяемого значения эквиваленту 0 для Long
   * @param _comparing_val Проверяемое значение
   * @return Результат проверки (=true - если равно 0)
   */
  public static boolean isZeroLong(Long _comparing_val) {
    return _comparing_val.equals(0L);
  }

  /**
   * Вычисление признака равенства проверяемого значения эквиваленту 0 для BigDecimal
   * @param _comparing_val Проверяемое значение
   * @return Результат проверки (=true - если равно 0)
   */
  public static boolean isZeroBigDecimal(BigDecimal _comparing_val) {
    return (BigDecimal.ZERO.compareTo(_comparing_val) == 0);
  }

  /**
   * Вычисление признака не равенства проверяемого значения эквиваленту 0 для BigDecimal
   * @param _comparing_val Проверяемое значение
   * @return Результат проверки (=true - если не равно 0)
   */
  public static boolean isNonZeroBigDecimal(BigDecimal _comparing_val) {
    return (BigDecimal.ZERO.compareTo(_comparing_val) != 0);
  }

  /**
   * Вычисление признака равенства проверяемого значения эквиваленту 0 или null - для BigDecimal
   * @param _comparing_val Проверяемое значение
   * @return Результат проверки (=true - если равно null или 0)
   */
  public static boolean isNullOrZeroBigDecimal(BigDecimal _comparing_val) {
    if (_comparing_val == null) return true;
    return (BigDecimal.ZERO.compareTo(_comparing_val) == 0);
  }

  /**
   * Вычисление признака равенства двух параметров BigDecimal
   * @param _comparing_val1 Проверяемое значение
   * @param _comparing_val2 Проверяемое значение
   * @return Результат проверки (=true - если значения равны)
   */
  public static boolean isEqualsBigDecimal(BigDecimal _comparing_val1, BigDecimal _comparing_val2) {
    return (_comparing_val1.compareTo(_comparing_val2) == 0);
  }

  /**
   * Вычисление признака равенства проверяемого значения с эквивалентом нуля,
   * для бизнес-решения заказчика, где число 0.0001 это 0 (зачем так???????)
   * @param _comparing_val Проверяемое значение
   * @return Результат проверки (=true - если равно 0.0001)
   */
  public static boolean isZeroEquvivAPrice(BigDecimal _comparing_val) {
    BigDecimal equviv_templ = BigDecimal.valueOf(0.0001);
    return (equviv_templ.compareTo(_comparing_val) == 0);
  }

  /**
   * Вычисление сценария когда проверяемое значение BigDecimal меньше 0
   * @param _comparing_val Проверяемое значение
   * @return Результат проверки
   */
  public static boolean isLessZeroBigDecimal(BigDecimal _comparing_val) {
    return (BigDecimal.ZERO.compareTo(_comparing_val) >= 0);
  }

  /**
   * Вычисление сценария когда проверяемое значение BigDecimal больше 0
   * @param _comparing_val Проверяемое значение
   * @return Результат проверки
   */
  public static boolean isGreaterZeroBigDecimal(BigDecimal _comparing_val) {
    return (BigDecimal.ZERO.compareTo(_comparing_val) <= 0);
  }

  /**
   * Вычисление сценария когда первое значение больше второго для BigDecimal
   * @param _first_val Первое значение
   * @param _second_val Второе значение
   * @return Результат проверки (=true - если первое значение больше второго)
   */
  public static boolean isFirstGreaterThanSecond(BigDecimal _first_val, BigDecimal _second_val) {
    return (_first_val.compareTo(_second_val) >= 1);
  }

  /**
   * Вычисление сценария когда первое значение меньше второго для BigDecimal
   * @param _first_val Первое значение
   * @param _second_val Второе значение
   * @return Результат проверки (=true - если первое значение меньше второго)
   */
  public static boolean isFirstLessThanSecond(BigDecimal _first_val, BigDecimal _second_val) {
    return (_first_val.compareTo(_second_val) >= 1);
  }

  /**
   * Получение числа формата BigDecimal, равного эквиваленту 1
   * @return Значение в эквиваленте 1
   */
  public static BigDecimal getBigDecimalOne() {
    return BigDecimal.ONE;
  }

  /**
   * Получение числа формата BigDecimal, равного эквиваленту 100
   * @return Значение в эквиваленте 100
   */
  public static BigDecimal getBigDecimal100() {
    return BigDecimal.valueOf(100l);
  }

  /**
   * Получение числа формата BigDecimal, равного эквиваленту 0
   * @return Значение в эквиваленте 0
   */
  public static BigDecimal getBigDecimalZero() {
    return BigDecimal.ZERO;
  }

  /**
   * Вычисление признака наличия (представления в систему)
   * кастомизированной даты расчёта
   * @param _customCalcDate Исследуемая дата
   * @return =true - если исследуемая дата больше чем LocalDate.MIN, иначе =false
   */
  public static Boolean isCustomCalcDatePresent(LocalDate _customCalcDate) {
    return !_customCalcDate.equals(LocalDate.MIN);
  }

  /**
   * Вычисление признака рублёвого полиса,
   * когда код валюты равен RUB
   * @param _currency_str_val Исследуемая строка с кодом
   * @return =true - если код равен RUB, =false - если код не равен RUB
   */
  public static Boolean isPolicyCurrencyRUB(String _currency_str_val) {
    return _currency_str_val.trim().toUpperCase().equals("RUB");
  }

  /**
   * Вычисление признака не рублёвого полиса,
   * когда код валюты не равен RUB
   * @param _currency_str_val Исследуемая строка с кодом
   * @return =true - если код не равен RUB, =false - если код равен RUB
   */
  public static Boolean isPolicyCurrencyNonRUB(String _currency_str_val) {
    Boolean result = false;
    String input_currency = _currency_str_val.trim().toUpperCase();
    if(!input_currency.equals("RUB")/* && input_currency.length() < 4*/) {
      // полис не рублёвый
      result = true;
    }
    return result;
  }

  /**
   * Вычисление признака идентичности валюты полиса
   * @param _first_val Исследуемая строка с кодом 1
   * @param _second_val Исследуемая строка с кодом 2
   * @return =true - если коды одинаковы, =false - если коды отличаются
   */
  public static Boolean isItemCurrenciesEqual(String _first_val, String _second_val) {
    return _first_val.trim().toUpperCase().equals(_second_val.trim().toUpperCase());
  }

  /**
   * Считаем что мультивалютное представление это всегда два кода валют,
   * разделённых символом пробела
   * @param _currency_str_val Строка мультивалютного представления
   * @return Если массив из представления больше одного элемента, то полис - мультивалютный
   */
  public static Boolean isPolicyMultiCurrency(String _currency_str_val) {
    return _currency_str_val.trim().toUpperCase().split("\\s+").length > 1;
  }

  /**
   * Получение дополнительной секции кода валюты в мультивалютном исполнении
   * Считаем: мультивалютное исполнение - всегда два кода валют, одна из которых всегда "RUB"
   * Даже если их больше и они не разделены пробелом, берётся первое что не "RUB"
   * @param _multi_currency_code Код мультивалюты
   * @return Код дополнительной к "RUB" части мультивалютного представления
   */
  public static String getMultiCurrencyAddPartCode(String _multi_currency_code) {
    String[] multi_currency_parts = _multi_currency_code.trim().split("\\s+");

    Optional<String> opt_currency_part = Arrays
        .stream(multi_currency_parts)
        .filter(m -> !m.toUpperCase().equals("RUB"))
        .findFirst();

    return opt_currency_part.orElse("").toUpperCase();
  }

  /**
   * Получение фактора наличия искомой строки в массиве предустановленных значений статусов полиса:
   * + "Действует"
   * + "Проект"
   * + "Завершен"
   * @param _target_contract_status Целевой проверяемый статус полиса
   * @return Фактор наличия (=true - есть наличие, =false - отсутствие)
   */
  public static boolean isPolicyStatusContainsFactor(String _target_contract_status) {
    return Stream.of(checked_status).anyMatch(n->n.equalsIgnoreCase(_target_contract_status));
  }

  /**
   * Получение фактора превышения или равенства даты которую сравниваем перед датой с которой сравниваем
   * @param _date_be_compared Дата которую сравниваем
   * @param _date_from_which_compare Дата с которой сравниваем
   * @return Фактор превышения или равенства первой даты со второй (=true - первая дата больше или равна второй, =false - обратно)
   */
  public static boolean isFirstGreaterOrEqualsSecond(LocalDate _date_be_compared, LocalDate _date_from_which_compare) {
    return (_date_be_compared.isEqual(_date_from_which_compare) || _date_be_compared.isAfter(_date_from_which_compare));
  }

  /**
   * Получение фактора не превышения и не равенства даты которую сравниваем перед датой с которой сравниваем
   * @param _date_be_compared Дата которую сравниваем
   * @param _date_from_which_compare Дата с которой сравниваем
   * @return Фактор не превышения или равенства первой даты со второй (=true - первая дата меньше второй, =false - обратно)
   */
  public static boolean isFirstLowerSecond(LocalDate _date_be_compared, LocalDate _date_from_which_compare) {
    return _date_be_compared.isBefore(_date_from_which_compare);
  }

  /**
   * Вычисляем признак равенства кода валюты RUB
   * @param _currency_code Строка кода валюты
   * @return Целевой признак (=true - если код валюты в любом кейсе RUB, =false - иначе)
   */
  public static boolean isCCodeRUB(String _currency_code) {
    return _currency_code.trim().equalsIgnoreCase("RUB");
  }

  /**
   * Вычисляем признак равенства кода валюты USD
   * @param _currency_code Строка кода валюты
   * @return Целевой признак (=true - если код валюты в любом кейсе USD, =false - иначе)
   */
  public static boolean isCCodeUSD(String _currency_code) {
    return _currency_code.trim().equalsIgnoreCase("USD");
  }
}