import com.google.protobuf.BoolValue;
import com.vach.common.proto.ProtoCommon;
import com.vach.sample.BooleanDto;
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

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestMappingWithTrueValue {

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[10][0]);  // hack to run tests 10 times
    }

    @Test
    public void convertDtoWithTrueToProtoBufGenerated() {
        ModelMapper modelMapper = getModelMapper();
        BooleanDto booleanDto = new BooleanDto();
        booleanDto.setSomeBoolValue(true);

        ProtoCommon.TestBoolValue testBoolValue = modelMapper.map(booleanDto, ProtoCommon.TestBoolValue.Builder.class).build();

        assertTrue(testBoolValue.getSomeBoolValue().getValue());
    }

    @Test
    public void convertProtoBufGeneratedWithTrueToDto() {
        ModelMapper modelMapper = getModelMapper();
        ProtoCommon.TestBoolValue testBoolValue = ProtoCommon.TestBoolValue.newBuilder().setSomeBoolValue(BoolValue.newBuilder().setValue(true).build()).build();

        BooleanDto booleanDto = modelMapper.map(testBoolValue, BooleanDto.class);

        assertTrue(booleanDto.getSomeBoolValue());
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
