package ru.alexanna.microwialon.wialonips.packettypes;


import ru.alexanna.microwialon.wialonips.util.ServiceIPS;

import java.util.Date;
import java.util.Objects;

/**
  *HDOP Horizontal dilution of precision — значение снижения точности в горизонтальной плоскости, показывает
  * точность передаваемых устройством координат. Чем меньше значение данного параметра, тем более
  * достоверными являются координаты. Если отсутствует, передается NA.
  *
  * Inputs Цифровые входы. Каждый бит числа соответствует одному входу, начиная с младшего. Целое число. Если
  * отсутствует, передается NA
  *
  * Outputs Цифровые выходы. Каждый бит числа соответствует одному выходу, начиная с младшего. Целое число. Если
  * отсутствует, передается NA.
  *
  * ADC Аналоговые входы. Дробные числа, через запятую. Нумерация датчика начинается с единицы. Если
  * аналоговые входы отсутствуют, передается пустая строка. Пример: 14.77,0.02,3.6
  *
  * Ibutton Код ключа водителя. Строка произвольной длины. Если отсутствует, передается NA.
  *
  * Params Дополнительные параметры. Разделяются запятой. См. «Дополнительные параметры»
  *
  *
*/

public class LongDataPacket extends AbstractDataPacket {
     private final Double hdop;
     private final Integer inputs;
     private final Integer outputs;
     private final Double[] adc;
     private final String iButton;
     private final Parameter params;

     private static class Parameter {
         private final String name;
         private final ParameterType type;
         private final String value;

         public Parameter(String name, ParameterType type, String value) {
             this.name = name;
             this.type = type;
             this.value = value;
         }

         @Override
         public String toString() {
             return (type == null || Objects.equals(name, "") || Objects.equals(value, ""))
                     ? "NA" : name + ":" + type.getValue() + ":" + value;
         }
     }

     public static class Builder extends AbstractDataPacket.Builder<LongDataPacket.Builder> {
         private Double hdop;
         private Integer inputs;
         private Integer outputs;
         private Double[] adc;
         private String iButton;
         private String paramName;
         private ParameterType paramType;
         private String paramValue;

         public Builder(Date date) {
             super(date);
         }

         public Builder(int year, int month, int date, int hourOfDate, int minute, int second) {
             super(year, month, date, hourOfDate, minute, second);
         }

         @Override
         public AbstractDataPacket create() {
             return new LongDataPacket(this);
         }

         @Override
         protected Builder self() {
             return this;
         }

         public Builder hdop(Double hdop) {
             this.hdop = hdop;
             return self();
         }

         public Builder inputs(Integer inputs) {
             this.inputs = inputs;
             return self();
         }

         public Builder outputs(Integer outputs) {
             this.outputs = outputs;
             return self();
         }

         public Builder adc(Double[] adc) {
             this.adc = adc;
             return self();
         }

         public Builder iButton(String iButton) {
             this.iButton = iButton;
             return self();
         }

         public Builder params(String name, ParameterType type, String value) {
             this.paramName = name;
             this.paramType = type;
             this.paramValue = value;
             return self();
         }
     }

     protected LongDataPacket(Builder builder) {
         super(builder);
         inputs = builder.inputs;
         outputs = builder.outputs;
         hdop = builder.hdop;
         adc = builder.adc;
         iButton = builder.iButton;
         params = new Parameter(builder.paramName, builder.paramType, builder.paramValue);
     }

     @Override
     public ClientPacketType getPacketType() {
         return ClientPacketType.D;
     }

     @Override
     public String getMessage() {
         return String.format("%s;%s;%s;%s;%s;%s;%s",
                 super.getMessage(),
                 ServiceIPS.getParamMsg(hdop),
                 ServiceIPS.getParamMsg(inputs),
                 ServiceIPS.getParamMsg(outputs),
                 ServiceIPS.getParamMsg(adc),
                 ServiceIPS.getParamMsg(iButton),
                 params);
     }

 }
