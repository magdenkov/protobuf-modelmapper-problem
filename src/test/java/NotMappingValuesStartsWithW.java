import com.vach.common.proto.ProtoCommon;
import com.vach.sample.ModelMapperFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class NotMappingValuesStartsWithW {

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


        assertTrue(builder.getPropertyDetails().getUtilities().getElectric());
        assertTrue(builder.getPropertyDetails().getUtilities().getGas());
        assertTrue(builder.getPropertyDetails().getUtilities().getSewer());
        assertTrue(builder.getPropertyDetails().getUtilities().getTrash());
        assertTrue(builder.getPropertyDetails().getUtilities().getInternet());
        assertTrue(builder.getPropertyDetails().getUtilities().getCable());
        assertTrue(builder.getPropertyDetails().getUtilities().getWater());  // some times it is false!!!
    }
}
