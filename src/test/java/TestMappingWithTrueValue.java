import com.google.protobuf.BoolValue;
import com.vach.common.proto.ProtoCommon;
import com.vach.sample.BooleanDto;
import com.vach.sample.ModelMapperFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestMappingWithTrueValue {

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[10][0]);  // hack to run tests 10 times
    }

    @Test
    public void convertDtoWithTrueToProtoBufGenerated() {
        ModelMapper modelMapper = ModelMapperFactory.getConfiguredModelMapper();
        BooleanDto booleanDto = new BooleanDto();
        booleanDto.setSomeBoolValue(true);

        ProtoCommon.TestBoolValue testBoolValue = modelMapper.map(booleanDto, ProtoCommon.TestBoolValue.Builder.class).build();

        assertTrue(testBoolValue.getSomeBoolValue().getValue());
    }

    @Test
    public void convertProtoBufGeneratedWithTrueToDto() {
        ModelMapper modelMapper = ModelMapperFactory.getConfiguredModelMapper();
        ProtoCommon.TestBoolValue testBoolValue = ProtoCommon.TestBoolValue.newBuilder().setSomeBoolValue(BoolValue.newBuilder().setValue(true).build()).build();

        BooleanDto booleanDto = modelMapper.map(testBoolValue, BooleanDto.class);

        assertTrue(booleanDto.getSomeBoolValue());
    }
}
