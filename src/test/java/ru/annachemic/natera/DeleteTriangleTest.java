package ru.annachemic.natera;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.annachemic.natera.rest.RestMethods;
import ru.annachemic.natera.services.DeleteTriangleService;
import ru.annachemic.natera.utils.RetrofitUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class DeleteTriangleTest {
    DeleteTriangleService deleteTriangleService;

    @BeforeMethod
    @SneakyThrows
    public void setUp() {
        deleteTriangleService = RetrofitUtils.getRetrofit().create(DeleteTriangleService.class);
    }


    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "getTrianglesList")
    @SneakyThrows
    public void deleteTriangleTest(List<String> triangleIdList) {
        Integer beforeListSize = triangleIdList.size();
        log.info("Deleting element with id  " + triangleIdList.get(0));
        retrofit2.Response<Void> response =
                deleteTriangleService.deleteTriangleById(
                        triangleIdList.get(0), "f78ed3e2-2e7d-4e54-8c41-ffbc715bea1c")
                        .execute();
        List<String> idListAfterDeletion = RestMethods.getAllTringlesIds(RestMethods.getAllTrianglesInfo());
        Integer afterDeleteArraySize = idListAfterDeletion.size();
        assertThat(afterDeleteArraySize).isEqualTo(beforeListSize - 1);
        triangleIdList.remove(0);
        assertThat(idListAfterDeletion).isEqualTo(triangleIdList);
    }
}
