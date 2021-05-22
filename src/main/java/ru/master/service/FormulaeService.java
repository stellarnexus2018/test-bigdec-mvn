package ru.master.service;

import lombok.extern.slf4j.Slf4j;
import ru.master.properties.PropContainer;
import ru.master.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
public class FormulaeService {
  PropContainer pc = new PropContainer();
  // region Доходность MWR

  /**
   * Рассчёт Доходность MWR
   * @param _calc_prot_id           ID протокола расчёта
   * @param _urgency                Срочность
   * @param _z_factor               Z-фактор
   * @param _calc_date              Дата расчёта
   * @param _insurance_start_date   Дата начала действия полиса
   * @param _guarantee_rate         Уровень гарантии
   * @param _insurance_prem_rub     Страховая премия (RUB)
   * @param _insurance_prem_nonrub  Страховая премия (nonRUB -> (USD))
   * @param _profitability          Доходность в денежном эквиваленте (ИДД на дату расчёта)
   * @param _policy_id              ID полиса
   * @return Доходность MWR
   */
  public BigDecimal getMwrYield(String      _calc_prot_id,
                                Long        _urgency,
                                BigDecimal  _z_factor,
                                LocalDate _calc_date,
                                LocalDate   _insurance_start_date,
                                BigDecimal  _guarantee_rate,
                                BigDecimal  _insurance_prem_rub,
                                BigDecimal  _insurance_prem_nonrub,
                                BigDecimal  _profitability,
                                Long        _policy_id) {
    // Отладка
    log.info("UID:{} (getMwrYield) Полис ID: {} _urgency(S) => {}",               _calc_prot_id, _policy_id, _urgency);
    log.info("UID:{} (getMwrYield) Полис ID: {} _z_factor(Z) => {}",              _calc_prot_id, _policy_id, _z_factor);
    log.info("UID:{} (getMwrYield) Полис ID: {} _calc_date(T) => {}",             _calc_prot_id, _policy_id, _calc_date);
    log.info("UID:{} (getMwrYield) Полис ID: {} _insurance_start_date(t0) => {}", _calc_prot_id, _policy_id, _insurance_start_date);
    log.info("UID:{} (getMwrYield) Полис ID: {} _guarantee_rate => {}",           _calc_prot_id, _policy_id, _guarantee_rate);
    log.info("UID:{} (getMwrYield) Полис ID: {} _insurance_prem_rub => {}",       _calc_prot_id, _policy_id, _insurance_prem_rub);
    log.info("UID:{} (getMwrYield) Полис ID: {} _insurance_prem_nonrub => {}",    _calc_prot_id, _policy_id, _insurance_prem_nonrub);
    log.info("UID:{} (getMwrYield) Полис ID: {} _profitability(ИДД) => {}",       _calc_prot_id, _policy_id, _profitability);

    /*
    Формула:

    max(("Доходность в денежном эквиваленте"/"Средневзвешенный денежный поток")-Z,0)+("уровень гарантии"-1)/S
      "Доходность в денежном эквиваленте" = ИДД
      "Средневзвешенный денежный поток" = (T-t0)/365*P0
      Z = z-фактор
      t0 = дата начала действия полиса
      P0 = страховая премия в зависимости от валюты полиса
      "уровень гарантии" - гарантия размер
      S - срочность полиса
    */

    // Контрольные проверки (отработка сценария возможного деления на 0 фиксируем в логах)
    if (_urgency == null || Utils.isZeroLong(_urgency)) {
      log.info("UID:{} (getMwrYield) Полис ID: {} Срочность = {}",
          _calc_prot_id, _policy_id, _urgency);
      return Utils.getBigDecimalZero();
    }

    BigDecimal P0 = null;
    if (_insurance_prem_rub != null && Utils.isNonZeroBigDecimal(_insurance_prem_rub)
        && _insurance_prem_nonrub != null && Utils.isNonZeroBigDecimal(_insurance_prem_nonrub)) {
      log.info("UID:{} (getMwrYield) Полис ID: {} сценарий проверки страховых премий, _insurance_prem_rub:{}, _insurance_prem_nonrub:{}",
          _calc_prot_id, _policy_id, _insurance_prem_rub, _insurance_prem_nonrub);
      return Utils.getBigDecimalZero();
    } else if (_insurance_prem_rub != null && Utils.isNonZeroBigDecimal(_insurance_prem_rub)) {
      P0 = _insurance_prem_rub;
    } else if (_insurance_prem_nonrub != null && Utils.isNonZeroBigDecimal(_insurance_prem_nonrub)) {
      P0 = _insurance_prem_nonrub;
    }

    log.info("UID:{} (getMwrYield) Полис ID: {} P0: {}",
        _calc_prot_id, _policy_id, P0);

    // Средневзвешенный денежный поток ((T-t0)/365*P0)
    double days_between = Math.abs(ChronoUnit
        .DAYS
        .between(_calc_date, _insurance_start_date));

    log.info("UID:{} (getMwrYield) Полис ID: {} (T-t0): {}",
        _calc_prot_id, _policy_id, days_between);

    BigDecimal weighted_average_cash_flow =
        BigDecimal.valueOf(days_between).divide((BigDecimal.valueOf(365).multiply(P0)), pc.getScaleBigDecForValue(), RoundingMode.HALF_UP);

    log.info("UID:{} (getMwrYield) Полис ID: {} ((T-t0)/365*P0) => ({}/365*{}) Средневзвешенный денежный поток: {}",
        _calc_prot_id, _policy_id, days_between, P0, weighted_average_cash_flow);

    // ("Доходность в денежном эквиваленте" / "Средневзвешенный денежный поток") - Z
    BigDecimal part1 = _profitability
        .divide(weighted_average_cash_flow, pc.getScaleBigDecForValue(), RoundingMode.HALF_UP)
        .subtract(_z_factor);

    // ("Уровень гарантии" - 1)/S
    BigDecimal part2 = (_guarantee_rate.subtract(Utils.getBigDecimalOne()))
        .divide(BigDecimal.valueOf(_urgency), pc.getScaleBigDecForValue(), RoundingMode.HALF_UP);

    BigDecimal result = (Utils.isGreaterZeroBigDecimal(part1) ? part1 : Utils.getBigDecimalZero())
        .add(part2);

    log.info("UID:{} (getMwrYield) Полис ID: {} (Доходность_в_денежном_эквиваленте/Средневзвешенный_денежный_поток)-Z:({}/{})-{} = {}",
        _calc_prot_id, _policy_id, _profitability, weighted_average_cash_flow, _z_factor, part1);
    log.info("UID:{} (getMwrYield) Полис ID: {} (уровень_гарантии-1)/S: ({}-1)/{} = {}",
        _calc_prot_id, _policy_id, _guarantee_rate, _urgency, part2);
    log.info("UID:{} (getMwrYield) Полис ID: {} result: {}", _calc_prot_id, _policy_id, result);

    return result;
  }

  // endregion Доходность MWR
}