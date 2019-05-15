package gred.nucleus.AnalyseTest;




import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;


public class OuputFileVerification {

    /** Key of files expected in the result directory */
    Map<String,String> _myMapInitialFilesInputFolder = new HashMap<String,String>();

    /** Key of files produce by the analysis*/
    Map<String,String> _myMapInitialFileOutputFolder = new HashMap<String,String>();

    /** list of files produce by the analysis*/
    Map<String,String> _myMapFilesProduceByAnlaysis = new HashMap<String,String>();


    public void OuputFileVerification() {}

    /**
    List files expected and compute md5sum stored in hashMap (read recursively folders)
     @param path : path of folder which contains files expected

     */

    public void GetFileResultExpeted( String path ) {
        File root = new File(path);
        File[] list = root.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                GetFileResultExpeted(f.getAbsolutePath());
            }
            else {
                try {
                    _myMapInitialFilesInputFolder.put(f.getName(), md5(f.getPath()));
                    }
                catch (IOException e){

                }
            }
        }

    }
    /**
     List files already inside the output folder and compute md5sum stored in hashMap (read recursively folders)
     @param path : path of folder which contains files expected

     */

    public void GetFilesOutputFolder( String path ) {
        File root = new File(path);
        File[] list = root.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                GetFilesOutputFolder(f.getAbsolutePath());
            }
            else {
                try {
                    _myMapInitialFileOutputFolder.put(f.getName(), md5(f.getPath()));
                }
                catch (IOException e){

                }
            }
        }

    }
    /**
     List files output folder produce by the analyse and compute md5sum stored in hashMap (read recursively folders)
     @param path : path of folder which contains files expected

     */
    public void GetFilesResultingOfAnalysis( String path ) {
        File root = new File(path);
        File[] list = root.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                GetFilesResultingOfAnalysis(f.getAbsolutePath());
            }
            else {
               try {
                   _myMapFilesProduceByAnlaysis.put(f.getName(), md5(f.getPath()));
               }
               catch (IOException e){

               }
            }
        }

    }
    /**
     *Method to compare md5sum of files from output analysis
     * with expected results
     *
     */
    public void CompareAnalysisResult() {
        for(Map.Entry<String, String> entry : _myMapInitialFilesInputFolder.entrySet()) {
            String fileName = entry.getKey();
            String hashCode = entry.getValue();
            if ( hashCode.equals( _myMapFilesProduceByAnlaysis.get(fileName))){
                System.out.println("Terrible du cul ");
            }
            else {

            }

        }
    }

    /**
     * Method to compute md5sum of files
     * @param path path of file
     * @return hash md5 of file
     * @throws IOException
     *  TODO ADD exception handeling
     */

    public String md5(String path) throws IOException  {
        String checksumMD5="Na";
        try {
            checksumMD5 = DigestUtils.md5Hex(new FileInputStream(path));
        }
        catch (IOException e){

        }
        return checksumMD5;

    }



}
