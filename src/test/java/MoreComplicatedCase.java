import com.google.protobuf.BoolValue;
import com.vach.common.proto.ProtoCommon;
import com.vach.sample.ModelMapperFactory;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MoreComplicatedCase {

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[10][0]);  // hack to run tests 10 times
    }

    @Test
    public void mappingTest() {
        ModelMapper modelMapper = ModelMapperFactory.getConfiguredModelMapper();
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

        System.out.println(builder.getPropertyDetails().getUtilities().getWater());
        assertNotNull(builder.getPropertyDetails().getUtilities().getWater());
        assertTrue(builder.getPropertyDetails().getUtilities().getWater());  // some times is is false!!!
    }
}
