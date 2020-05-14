package ru.annachemic.natera;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.annachemic.natera.rest.RestMethods;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.annachemic.natera.rest.RestMethods.getAllTrianglesInfo;
import static ru.annachemic.natera.rest.RestMethods.getAllTringlesIds;

@Slf4j
public class DeleteTriangleTest {


    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "getNewTrianglesList")
    @SneakyThrows
    public void deleteTriangleTest(List<String> triangleIdList) {
        int beforeListSize = triangleIdList.size();
        RestMethods.deleteATriangle(triangleIdList.get(0), 200);

        List<String> idListAfterDeletion = getAllTringlesIds(getAllTrianglesInfo());
        Integer idListAfterDeletionSize = idListAfterDeletion.size();

        assertThat(idListAfterDeletionSize).isEqualTo(beforeListSize - 1);
        triangleIdList.remove(0);
        assertThat(idListAfterDeletion).isEqualTo(triangleIdList);
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "getNewTrianglesList")
    public void deleteTriangleWithoutAuth(List<String> triangleIdList) {
        Integer beforeListSize = triangleIdList.size();
        RestMethods.deleteATriangle(triangleIdList.get(0), "", 401);

        List<String> idListAfterDeletion = getAllTringlesIds(getAllTrianglesInfo());
        Integer idListAfterDeletionSize = idListAfterDeletion.size();

        assertThat(idListAfterDeletionSize).isEqualTo(beforeListSize);
        assertThat(idListAfterDeletion).isEqualTo(triangleIdList);
    }

    @Test
    public void deleteTriangleWithEmptyId() {
        List<String> idListBeforeDeletion = getAllTringlesIds(getAllTrianglesInfo());
        RestMethods.deleteATriangle("", 405);
        List<String> idListAfterDeletion = getAllTringlesIds(getAllTrianglesInfo());
        assertThat(idListAfterDeletion).isEqualTo(idListBeforeDeletion);
    }
}
