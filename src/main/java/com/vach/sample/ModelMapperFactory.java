package com.vach.sample;

import com.google.protobuf.BoolValue;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import java.util.Optional;


public class ModelMapperFactory {

    public static ModelMapper getConfiguredModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(GRPC_BOOL_WRAPPER_TO_BOOLEAN);
        modelMapper.addConverter(BOOLEAN_TO_GRPC_WRAPPER_BOOL);
        return modelMapper;
    }

    private static final Converter GRPC_BOOL_WRAPPER_TO_BOOLEAN = new Converter<BoolValue, Boolean>() {
        @Override
        public Boolean convert(MappingContext<BoolValue, Boolean> context) {
            System.out.println("Converter GRPC_BOOL_WRAPPER_TO_BOOLEAN executed ");
            return Optional.ofNullable(context.getSource())
                    .map(BoolValue::getValue)
                    .orElse(null);
        }
    };

    private static final Converter BOOLEAN_TO_GRPC_WRAPPER_BOOL = new Converter<Boolean, BoolValue>() {
        @Override
        public BoolValue convert(MappingContext<Boolean, BoolValue> context) {
            System.out.println("Converter BOOLEAN_TO_GRPC_WRAPPER_BOOL executed ");
            return Optional.ofNullable(context.getSource())
                    .map(v -> BoolValue.newBuilder().setValue(v).build())
                    .orElse(null);
        }
    };

}
