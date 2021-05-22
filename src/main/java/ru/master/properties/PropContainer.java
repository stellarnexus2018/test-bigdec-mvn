package ru.master.properties;

import lombok.Data;

@Data
public class PropContainer {
  private int scaleBigDecForPrice = 4;
  private int scaleBigDecForValue = 8;
  private int resFundRatePrecountYears;
  private String timeZoneVal;
  private String currentServName;
  private String simpleDateFormatStr;
  private String keyMapPolicyLstStr;
  private String keyCustomCalcDateStr;
  private String clineCustomCalcDateParamName;
  private String keyBusinessKeyStr;
  private Boolean useJammedPolChanDBSearch;
  private Boolean disableSaveOnDebugMode;
  private Boolean enableCalcDateRuntime;
}