import com.google.protobuf.BoolValue;
import com.vach.common.proto.ProtoCommon;
import com.vach.sample.BooleanDto;
import org.junit.Ignore;
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

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;


@RunWith(Parameterized.class)
public class TestMappingWithNULLValue {

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[10][0]);  // hack to run tests 10 times
    }

    @Test
    @Ignore("This will always fail, since by default will be returned false, so need to create some workaround for this case")
    public void convertProtoBufGeneratedWithNullToDto() {
        ModelMapper modelMapper = getModelMapper();
        ProtoCommon.TestBoolValue testBoolValue = ProtoCommon.TestBoolValue.newBuilder().build();
        assertFalse(testBoolValue.hasSomeBoolValue());

        BooleanDto dto = modelMapper.map(testBoolValue, BooleanDto.class);

        assertNull(dto.getSomeBoolValue());  // this will fail since default will be always false
    }

    @Test
    public void convertDtoWithNullToProtoBufGenerated() {
        ModelMapper modelMapper = getModelMapper();
        BooleanDto booleanDto = new BooleanDto();
        booleanDto.setSomeBoolValue(null);

        ProtoCommon.TestBoolValue boolValueDto = modelMapper.map(booleanDto, ProtoCommon.TestBoolValue.Builder.class).build();

        assertFalse(boolValueDto.hasSomeBoolValue());
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
