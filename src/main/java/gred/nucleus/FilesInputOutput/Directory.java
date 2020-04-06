package gred.nucleus.FilesInputOutput;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

/**
 * Class get to list directory and sub directory.
 *
 *
 */


public class Directory  {

    /** Directory path */
    public File m_directory;
    /** Directory path */
    public String m_dirPath = "";
    /** List of files in current forlder + recursive folder */
    public ArrayList<File> m_listeOfFiles = new ArrayList<File>();
    /** Check if directory contain nd files */
    public Boolean m_containNdFile=false;
    /** List of nd files */
    public ArrayList<File> m_listeOfFilesND = new ArrayList<File>();
    /** */
    public String m_separator;

    /** Constructor
     * @param Path of directory
     */
    public Directory(String Path) {
        this.m_dirPath = Path;
        this.m_directory=new File(this.m_dirPath);
        this.m_separator=this.m_directory.separator;
    }

    /**
     * Method to check if directory and create if doesn't
     */
    public void CheckAndCreateDir() {
        ChekSeparatorEndPath();
        CreateDire();

    }

    /**
     * Check if separator exist
     */
    private void ChekSeparatorEndPath() {
        File dir = new File(this.m_dirPath);
        Character SeparatorEnd = this.m_dirPath.charAt(this.m_dirPath.length() - 1);
        if (!(SeparatorEnd.equals(dir.separator))) {
            this.m_dirPath = this.m_dirPath + dir.separator;

        }

    }

    /**
     * Method creating folder if doesn't exist.
     */
    private void CreateDire() {
        File dir = new File(this.m_dirPath);
        if (!dir.exists()) {
            dir.mkdir();
            System.out.println("New directory "+ this.m_dirPath);
        }
    }

    /**
     *
     * @return path current directory
     */
    public String get_dirPath() {
        return this.m_dirPath;
    }

    /**
     * Method to recursively list files contains in folder and sub folder. (Argument needed because of recursive way)
     * @param Path path of folder
     */
    public void listImageFiles(String Path) {
        File root = new File(Path);
        File[] list = root.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                listImageFiles(f.getAbsolutePath());
            }
            else {
                if(!(FilenameUtils.getExtension(f.getName()).equals("txt"))){
                    this.m_listeOfFiles.add(f);
                    if (FilenameUtils.getExtension(f.getName()).equals("nd")) {
                        this.m_containNdFile = true;
                        this.m_listeOfFilesND.add(f);
                    }
                }
            }
        }
    }
    public void listAllFiles(String Path) {
        File root = new File(Path);
        File[] list = root.listFiles();

        for (File f : list) {
            this.m_listeOfFiles.add(f);
            if (f.isDirectory()) {
                listAllFiles(f.getAbsolutePath());

            }

        }
    }

    /**
     * Replace list files if ND files have been listed.
     */
    public void checkAndActualiseNDFiles(){
        if (this.m_containNdFile == true) {
           this.m_listeOfFiles = this.m_listeOfFilesND;
        }
    }

    /**
     * check if input directory is empty
     */
    public void checkIfEmpty(){
        if(this.m_listeOfFiles.isEmpty()) {
            System.err.println("Folder "+this.m_dirPath+" is empty");
        }

    }
    /**
     * @return list of files
     */
    public ArrayList<File> ListFiles() {
        return this.m_listeOfFiles;
    }

    /**
     * @param indice of file in list array
     * @return File
     */
    public File getFile(int indice) {
        return this.m_listeOfFiles.get(indice);
    }

    /**
     * @return number of file listed
     */

    public int getNumberFiles() {
        return this.m_listeOfFiles.size();
    }
    public String getdirPath(){
        return this.m_directory.getPath()+File.separator;
    }

    public String getSeparator(){return this.m_separator;}

    /*
    * search a file in a list file without extension.
    * Use to compare 2 lists of files
     */
    public File searchFileNameWithoutExention(String fileName){
        File fileToReturn =null;

        for (File f : this.m_listeOfFiles) {
            if(f.getName().split("\\.")[0].equals(fileName)){
                fileToReturn=f;
            }
        }
        return fileToReturn;
    }

}
