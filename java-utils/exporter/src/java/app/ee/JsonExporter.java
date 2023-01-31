package app.ee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class JsonExporter {

    private final Logger logger;

    @Value("${app.output-directory}")
    private String outputDirectory;

    @Value("${app.output-filename}")
    private String outputFilename;

    public JsonExporter() {
        this.logger = LoggerFactory.getLogger(JsonExporter.class);
    }

    public void export2Json(List<SqlRowSet> rowSets) {
        JSONArray json = joinResultSetsInJsonArray(rowSets);

        String path = System.getProperty("user.home")
                           + File.separator
                           + outputDirectory
                           + File.separator
                           + outputFilename;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write('{');
            json.write(bufferedWriter);
            bufferedWriter.write('}');
        } catch (Exception e) {
            logger.error("Failed to write json array");
        }
    }

    private JSONArray joinResultSetsInJsonArray(List<SqlRowSet> rowSets) {
        List<JSONObject> jsonObjects = new LinkedList<>();
        for (SqlRowSet rowSet : rowSets) {
            SqlRowSetMetaData md = rowSet.getMetaData();
            int numCols = md.getColumnCount();
            List<String> colNames
                    = IntStream.range(0, numCols)
                               .mapToObj(i -> {
                                   return md.getColumnName(i + 1);
                               })
                               .collect(Collectors.toList());
            while (rowSet.next()) {
                JSONObject row = new JSONObject();
                colNames.forEach(cn -> {
                    try {
                        row.put(cn, rowSet.getObject(cn));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                jsonObjects.add(row);
            }
        }
        return new JSONArray(jsonObjects);
    }

}
