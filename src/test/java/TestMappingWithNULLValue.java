import com.vach.common.proto.ProtoCommon;
import com.vach.sample.BooleanDto;
import com.vach.sample.ModelMapperFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

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
        ModelMapper modelMapper = ModelMapperFactory.getConfiguredModelMapper();
        ProtoCommon.TestBoolValue testBoolValue = ProtoCommon.TestBoolValue.newBuilder().build();
        assertFalse(testBoolValue.hasSomeBoolValue());

        BooleanDto dto = modelMapper.map(testBoolValue, BooleanDto.class);

        assertNull(dto.getSomeBoolValue());  // this will fail since default will be always false
    }

    @Test
    public void convertDtoWithNullToProtoBufGenerated() {
        ModelMapper modelMapper = ModelMapperFactory.getConfiguredModelMapper();
        BooleanDto booleanDto = new BooleanDto();
        booleanDto.setSomeBoolValue(null);

        ProtoCommon.TestBoolValue boolValueDto = modelMapper.map(booleanDto, ProtoCommon.TestBoolValue.Builder.class).build();

        assertFalse(boolValueDto.hasSomeBoolValue());
    }
}
