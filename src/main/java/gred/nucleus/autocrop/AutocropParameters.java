package gred.nucleus.autocrop;

import gred.nucleus.plugins.PluginParameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class extend plugin parameters and contain the list of specific
 * parameters available for Autocrop function.
 */

public class AutocropParameters extends PluginParameters {
    /**
     * Number of pixels take plus object size in x
     */
    private int m_xCropBoxSize = 40;
    /**
     * Number of pixels take plus object size in y
     */
    private int m_yCropBoxSize = 40;
    /**
     * Number of slice take plus object in y
     */
    private int m_zCropBoxSize = 20;

    /**
     * Minimal object volume to crop
     */
    int m_minVolumeNucleus=1;
    /**
     * Maximal object volume to crop
     */
    int m_maxVolumeNucleus=2147483647;

    /**
     * Minimal default OTSU threshold
     */
    private int m_thresholdOTSUcomputing = 20;
    /**
     * Channel to compute OTSU threshold
     */
    private int m_channelToComputeThreshold=0;
    /**
     * Slice start to compute OTSU threshold
     */
    private int m_slicesOTSUcomputing=0;
    /**
     * Surface percent of boxes to groups them
     */
    private int m_boxesPercentSurfaceToFilter=50;
    /**
     * Activation of boxes regroupement
     */
    private boolean m_boxesRegroupement=true;

    /** Constructor with default parameter
     * @param inputFolder : path folder containing Images
     * @param outputFolder : path folder output analyse
     *
     */
    public AutocropParameters(String inputFolder, String outputFolder){
        super(inputFolder, outputFolder);

    }
    /** Constructor with box size modifications
     * @param inputFolder : path folder containing Images
     * @param outputFolder : path folder output analyse
     * @param xCropBoxSize : number of voxels add in x axis around object
     * @param yCropBoxSize : number of voxels add in z axis around object
     * @param zCropBoxSize : number of stack add in z axis around object
     */
    public AutocropParameters(String inputFolder, String outputFolder,
                              int xCropBoxSize,
                              int yCropBoxSize,
                              int zCropBoxSize,
                              int thresholdOTSUcomputing,
                              int channelToComputeThreshold) {
        super(inputFolder, outputFolder);
        this.m_xCropBoxSize = xCropBoxSize;
        this.m_yCropBoxSize = yCropBoxSize;
        this.m_zCropBoxSize = zCropBoxSize;
        this.m_thresholdOTSUcomputing = thresholdOTSUcomputing;
        this.m_channelToComputeThreshold=channelToComputeThreshold;
    }
    /** Constructor with all manual parameters
     * @param inputFolder : path folder containing Images
     * @param outputFolder : path folder output analyse
     * @param xCropBoxSize : number of voxels add in x axis around object
     * @param yCropBoxSize : number of voxels add in z axis around object
     * @param zCropBoxSize : number of stack add in z axis around object
     * @param channelToComputeThreshold : channel number to compute OTSU
     * @param slicesOTSUcomputing : slice start to compute OTSU
     * @param thresholdOTSUcomputing : minimum OTSU threshold used
     * @param maxVolumeNucleus : volume maximum of objects detected
     * @param minVolumeNucleus : volume minimum of objects detected
     */
    public AutocropParameters(String inputFolder, String outputFolder,
                              int xCropBoxSize,
                              int yCropBoxSize,
                              int zCropBoxSize,
                              int slicesOTSUcomputing,
                              int thresholdOTSUcomputing,
                              int channelToComputeThreshold,
                              int minVolumeNucleus,
                              int maxVolumeNucleus) {

        super(inputFolder, outputFolder);
        this.m_xCropBoxSize = xCropBoxSize;
        this.m_yCropBoxSize = yCropBoxSize;
        this.m_zCropBoxSize = zCropBoxSize;
        this.m_thresholdOTSUcomputing = thresholdOTSUcomputing;
        this.m_slicesOTSUcomputing = slicesOTSUcomputing;
        this.m_channelToComputeThreshold=channelToComputeThreshold;
        this.m_maxVolumeNucleus=maxVolumeNucleus;
        this.m_minVolumeNucleus=minVolumeNucleus;

    }
    /**
     * Constructor with box size modification and slice number used to start
     * OTSU threshold calculation
     * to last slice
     * @param inputFolder : path folder containing Images
     * @param outputFolder : path folder output analyse
     * @param xCropBoxSize : number of voxels add in x axis around object
     * @param yCropBoxSize : number of voxels add in z axis around object
     * @param zCropBoxSize : number of stack add in z axis around object
     * @param slicesOTSUcomputing : slice from OTSU threshold calculation start
     *
     */
    public AutocropParameters(String inputFolder, String outputFolder,
                              double xCal,
                              double yCal,
                              double zCal,
                              int xCropBoxSize,
                              int yCropBoxSize,
                              int zCropBoxSize,
                              int slicesOTSUcomputing,
                              int thresholdOTSUcomputing,
                              int channelToComputeThreshold,
                              int minVolumeNucleus,
                              int maxVolumeNucleus) {

        super(inputFolder, outputFolder, xCal, yCal, zCal);
        this.m_xCropBoxSize = xCropBoxSize;
        this.m_yCropBoxSize = yCropBoxSize;
        this.m_zCropBoxSize = zCropBoxSize;
        this.m_thresholdOTSUcomputing = thresholdOTSUcomputing;
        this.m_slicesOTSUcomputing = slicesOTSUcomputing;
        this.m_channelToComputeThreshold=channelToComputeThreshold;
        this.m_maxVolumeNucleus=maxVolumeNucleus;
        this.m_minVolumeNucleus=minVolumeNucleus;

    }

    /**
     * Constructor using input , output folders and config file (for command
     * line execution)
     * @param inputFolder : path folder containing Images
     * @param outputFolder : path folder output analyse
     * @param pathToConfigFile : path to the config file
     */
    public AutocropParameters (String inputFolder, String outputFolder,
                               String pathToConfigFile){
        super(inputFolder, outputFolder,pathToConfigFile);
        Properties prop = new Properties();
        String fileName = pathToConfigFile;
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            System.err.println(pathToConfigFile+" : can't find the config file !");
            System.exit(-1);
        }
        try {
            prop.load(is);
        } catch (IOException ex) {
            System.err.println(pathToConfigFile+ " : can't load the config file !");
            System.exit(-1);
        }
        for (String idProp :prop.stringPropertyNames()){
            if(idProp.equals("xCropBoxSize")){this.m_xCropBoxSize =
                    Integer.valueOf(prop.getProperty("xCropBoxSize"));}
            if(idProp.equals("yCropBoxSize")){this.m_yCropBoxSize =
                    Integer.valueOf(prop.getProperty("yCropBoxSize"));}
            if(idProp.equals("zCropBoxSize")){this.m_zCropBoxSize =
                    Integer.valueOf(prop.getProperty("zCropBoxSize"));}
            if(idProp.equals("thresholdOTSUcomputing")){
                this.m_thresholdOTSUcomputing =
                   Integer.valueOf(prop.getProperty("thresholdOTSUcomputing"));}
            if(idProp.equals("slicesOTSUcomputing")){
                this.m_slicesOTSUcomputing =
                    Integer.valueOf(prop.getProperty("slicesOTSUcomputing"));}
            if(idProp.equals("channelToComputeThreshold")){
                this.m_channelToComputeThreshold =
                Integer.valueOf(prop.getProperty("channelToComputeThreshold"));}
            if(idProp.equals("maxVolumeNucleus")){
                this.m_maxVolumeNucleus =
                    Integer.valueOf(prop.getProperty("maxVolumeNucleus"));}
            if(idProp.equals("minVolumeNucleus")){this.m_minVolumeNucleus =
                    Integer.valueOf(prop.getProperty("minVolumeNucleus"));}
            if(idProp.equals("boxesPercentSurfaceToFilter")){this.m_boxesPercentSurfaceToFilter =
                    Integer.valueOf(prop.getProperty("boxesPercentSurfaceToFilter"));}
            if(idProp.equals("boxesRegroupement")){this.m_boxesRegroupement =
                    Boolean.valueOf(prop.getProperty("boxesRegroupement"));}

        }
    }

     /**
     * Method to get parameters of the analyse
     * @return : list of the parameters used for the analyse
     */
    public String getAnalyseParameters() {
        super.getAnalyseParameters();
        this.m_headerInfo+="#X box size: "+getxCropBoxSize()+"\n"
             +"#Y box size: "+getyCropBoxSize()+"\n"
             +"#Z box size: "+getzCropBoxSize()+"\n"
             +"#thresholdOTSUcomputing: "+getThresholdOTSUcomputing()+"\n"
             +"#slicesOTSUcomputing: "  +getSlicesOTSUcomputing()+"\n"
             +"#channelToComputeThreshold: "+getChannelToComputeThreshold()+"\n"
             +"#maxVolumeNucleus:"+getM_maxVolumeNucleus()+"\n"
             +"#minVolumeNucleus: "+getM_minVolumeNucleus()+"\n";
        return this.m_headerInfo;
    }

    /**
     * Getter for x box size in pixel
     * @return x box size in pixel
     */
    public int getxCropBoxSize(){
        return this.m_xCropBoxSize;
    }
    /**
     * Getter for y box size in pixel
     * @return y box size in pixel
     */
    public int getyCropBoxSize(){
        return this.m_yCropBoxSize;
    }
    /**
     * Getter for z box size in pixel
     * @return z box size in pixel
     */
    public int getzCropBoxSize(){
        return this.m_zCropBoxSize;
    }
    /**
     * Getter for OTSU threshold used to compute segmented image
     * @return OTSU threshold used
     */
    public int getThresholdOTSUcomputing(){
        return this.m_thresholdOTSUcomputing;
    }
    /**
     * Getter for channel number used to segmented image (OTSU computing)
     * @return channel number
     */
    public int getChannelToComputeThreshold(){
        return this.m_channelToComputeThreshold;
    }
    /**
     * Getter for minimum volume object segmented
     * @return minimum volume
     */
    public int getM_minVolumeNucleus(){
        return this.m_minVolumeNucleus;
    }
    /**
     * Getter for maximum volume object segmented
     * @return maximum volume
     */
    public int getM_maxVolumeNucleus(){
        return  this.m_maxVolumeNucleus;
    }
    /**
     * Getter for start slice used to compute OTSU
     * @return start slice
     */
    public int getSlicesOTSUcomputing(){
        return this.m_slicesOTSUcomputing;
    }
    /**
     * Getter boxes merging activation
     * @return status
     */
    public boolean getboxesRegroupement(){
        return this.m_boxesRegroupement;
    }
    /**
     * Getter percent of surface intersection to merge 2 rectangles.
     * @return percentage surface
     */
    public int getBoxesPercentSurfaceToFilter(){return this.m_boxesPercentSurfaceToFilter;}
}
