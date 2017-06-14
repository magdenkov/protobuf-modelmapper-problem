package com.vach.sample;

import com.google.protobuf.BoolValue;
import com.vach.common.proto.ProtoCommon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MoreComplicatedCase {

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[10][0]);  // hack to run tests 10 times
    }

    @Test
    public void mappingTest() {
        ModelMapper modelMapper = getModelMapper();
        ProtoCommon.Property property = ProtoCommon.Property.newBuilder()
                .setPropertyId("propertyId")
                .setPropertyDetails(ProtoCommon.PropertyDetails.newBuilder()
                        .setAddress("address")
                        .setUtilities(ProtoCommon.PropertyDetails.Utilities.newBuilder()
                                .setElectric(true)
                                .setGas(true)
                                .setWater(true)
                                .setSewer(true)
                                .setTrash(true)
                                .setInternet(true)
                                .setCable(true)))
                .build();

        ProtoCommon.AddPropertyRequest builder = modelMapper.map(property, ProtoCommon.AddPropertyRequest.Builder.class).build();

        assertTrue(builder.getPropertyDetails().getUtilities().getWater());
    }


    private ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(GRPC_BOOL_WRAPPER_TO_BOOLEAN);
        modelMapper.addConverter(BOOLEAN_TO_GRPC_WRAPPER_BOOL);
        return modelMapper;
    }

    private final Converter GRPC_BOOL_WRAPPER_TO_BOOLEAN = new Converter<BoolValue, Boolean>() {
        @Override
        public Boolean convert(MappingContext<BoolValue, Boolean> context) {
            System.out.println("Converter GRPC_BOOL_WRAPPER_TO_BOOLEAN executed ");
            return Optional.ofNullable(context.getSource())
                    .map(BoolValue::getValue)
                    .orElse(null);
        }
    };

    private final Converter BOOLEAN_TO_GRPC_WRAPPER_BOOL = new Converter<Boolean, BoolValue>() {
        @Override
        public BoolValue convert(MappingContext<Boolean, BoolValue> context) {
            System.out.println("Converter BOOLEAN_TO_GRPC_WRAPPER_BOOL executed ");
            return Optional.ofNullable(context.getSource())
                    .map(v -> BoolValue.newBuilder().setValue(v).build())
                    .orElse(null);
        }
    };
}
