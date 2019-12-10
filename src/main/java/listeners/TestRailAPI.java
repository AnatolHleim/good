package listeners;

import listeners.core.APIClient;
import listeners.core.APIException;
import org.json.simple.JSONObject;
import utilites.TestData;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TestRailAPI {
    TestRailAPI() {
    }

    private static String url = TestData.URL_TEST_RAIL.getStringProperties();
    //username to login to TestRail
    private static String username = TestData.ACCOUNT_USER_TEST_RAIL.getStringProperties();
    //password to login to TestRail
    private static String password = TestData.PASS_USER_TEST_RAIL.getStringProperties();

    private static APIClient client() {
        APIClient client = new APIClient(url);
        client.setUser(username);
        client.setPassword(password);

        return client;
    }

    public static JSONObject getCase(int currentCaseId) throws APIException, IOException {
        APIClient client = TestRailAPI.client();
        return (JSONObject) client.sendGet("get_case/" + currentCaseId);
    }

    static JSONObject addResult(int pStatusId, String pComment, int pRunId, int pCaseId) throws APIException, IOException {
        disableSslVerification();
        APIClient client = TestRailAPI.client();
        HashMap<String, java.io.Serializable> data = new HashMap<>();
        data.put("status_id", pStatusId);
        data.put("comment", pComment);
        return (JSONObject) client.sendPost("add_result_for_case/" + pRunId + "/" + pCaseId, data);
    }

    static JSONObject addRun(int runId) throws APIException, IOException {
        disableSslVerification();
        APIClient client = TestRailAPI.client();
        Map data = new HashMap<>();
        data.put("include_all", true);
        return getJsonObject(runId, client, data);
    }

    static JSONObject addRun(int runId, int[] list) throws APIException, IOException {
        disableSslVerification();
        APIClient client = TestRailAPI.client();
        Map<String, Object> data = new HashMap<>();
        data.put("include_all", false);
        data.put("case_ids", Arrays.stream(list).boxed().collect(Collectors.toList()));
        return getJsonObject(runId, client, data);
    }

    private static JSONObject getJsonObject(int runId, APIClient client, Map<String, Object> data) throws IOException, APIException {
        data.put("assignedto_id", TestData.ASSIGNED_TO.getIntegerProperties());
        data.put("name", TestData.TEST_RUN_NAME.getStringProperties());
        data.put("description", TestData.TEST_DESCRIPTION.getStringProperties());
        return (JSONObject) client.sendPost("add_run/" + runId, data);
    }

    private static void disableSslVerification() {
        try {

            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
