package com.project.utils;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.project.common.WebApplication;
import com.project.model.PicStore;
import com.project.service.PicStoreService;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

/**
 * @author 朱源
 */
public class ImageUtils {

    //保存base64编码图片
    @SuppressWarnings("deprecation")
    public static String saveBase64(String imgFile, String savePath,
                                    String filename, Long maxSize) throws Exception {
        if (imgFile == null || imgFile.isEmpty())
            return "false";
        if (savePath == null || filename == null)
            return "false";
        if (maxSize == null)
            maxSize = 1024 * 1024 * 30L;
        //若类型不为图片，则不保存
        String type = imgFile.substring(imgFile.indexOf(":") + 1, imgFile.indexOf("/"));
        if (type == null || !type.equals("image"))
            return "false";

        //取扩展名
        String ext = imgFile.substring(imgFile.indexOf("/") + 1, imgFile.indexOf(";"));

        //取出base64编码内容,并保存
        imgFile = imgFile.substring(imgFile.indexOf(",") + 1);
        byte[] decoded = Base64.decode(imgFile.getBytes());
        if (decoded.length > maxSize)
            return "false";
        File dir = new File(savePath);
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        File file = new File(savePath + "\\" + filename + "." + ext);
        FileOutputStream write = new FileOutputStream(file);
        write.write(decoded);
        write.flush();
        write.close();
        return filename + "." + ext;
    }

    //保存file类型图片
    public static String save(File imgFile, String savePath,
                              String originName, String filename, Long maxSize) throws Exception {

        if (imgFile == null)
            return "false";
        if (maxSize == null)
            maxSize = 1024 * 1024 * 30L;

        // 定义一个数组，用于保存可上传的文件类型
        List<String> fileTypes = new ArrayList<String>();
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("png");
        fileTypes.add("bmp");
        // 保存第一张图片
        if (originName != null && imgFile.length() <= maxSize) {

            // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
            String ext = originName.substring(originName.lastIndexOf(".") + 1,
                    originName.length());
            // 对扩展名进行小写转换
            ext = ext.toLowerCase();

            if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件
                //创建保存文件的目录
                File dir = new File(savePath);
                if (dir.exists() == false) {
                    dir.mkdirs();
                }

                //将临时文件复制到保存目录下
                String file = savePath + "\\" + filename + "." + ext;

                File outputFile = new File(file);
                FileInputStream fis = new FileInputStream(imgFile);
                FileOutputStream fos = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int byteread = -1;
                while ((byteread = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteread);
                }

                fos.flush();
                fos.close();
                fis.close();
                //删除上传图片的临时文件
                imgFile.delete();
                return filename + "." + ext;
            } else
                return "false";
        } else
            return "false";
    }

    //生成缩略图

    /**
     * @return 返回处理后的图像
     */
    public static BufferedImage zoomImage(String src) {
        BufferedImage result = null;
        try {
            File srcfile = new File(src);
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
            }

            BufferedImage im = null;
            try {
                im = ImageIO.read(srcfile);
            } catch (Exception ex) {
                //图片是cmyk格式的,则无法直接读取,需要转换
                readImage(src);
                im = ImageIO.read(srcfile);
            }  

            /* 原始图像的宽度和高度 */
            int width = im.getWidth();
            int height = im.getHeight();
            int dstMaxSize = 250;//目标缩略图的最大宽度/高度，宽度与高度将按比例缩写
            int toWidth = width;
            int toHeight = height;
            //压缩计算  
            float resizeTimes = 0;  /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/
            //计算缩略图的宽和高
            /* 调整后的图片的宽度和高度 */
            //采取定宽的样式
            if (width > dstMaxSize) {
                toWidth = dstMaxSize;
                resizeTimes = (float) width / (float) dstMaxSize;
                toHeight = Math.round((float) height / resizeTimes);
            } else {
                resizeTimes = 1;
            }
            
            /* 新生成结果图片 */
            result = new BufferedImage(toWidth, toHeight,
                    BufferedImage.TYPE_INT_RGB);

            result.getGraphics().drawImage(
                    im.getScaledInstance(toWidth, toHeight,
                            java.awt.Image.SCALE_SMOOTH), 0, 0, null);

        } catch (Exception e) {
            System.out.println("创建缩略图发生异常" + e.getMessage());
        }

        return result;
    }

    public static boolean writeHighQuality(BufferedImage im, String fileFullPath) {
        try {
			/*输出到文件流*/
            FileOutputStream newimage = new FileOutputStream(fileFullPath);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
	        /* 压缩质量 */
            jep.setQuality(0.9f, true);
            encoder.encode(im, jep);
	        /*近JPEG编码*/
            newimage.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //缩略图使用实例
	/* public static void main(String[] args) {  
		String inputFoler = "D:\\img.jpg" ;   
		//这儿填写你存放要缩小图片的文件夹全地址  
		String outputFolder = "D:\\newImg.jpg";    
	    //这儿填写你转化后的图片存放的文件夹  
		ImageCompress2 narrowImage = new ImageCompress2();  
		narrowImage.writeHighQuality(narrowImage.zoomImage(inputFoler), outputFolder);  
	          
	} */

    //将CMYK格式转为rgb
    public static void readImage(String filename) throws IOException {
        File file = new File(filename);
        ImageInputStream input = ImageIO.createImageInputStream(file);
        Iterator readers = ImageIO.getImageReaders(input);
        if (readers == null || !readers.hasNext()) {
            throw new RuntimeException("1 No ImageReaders found");
        }
        ImageReader reader = (ImageReader) readers.next();
        reader.setInput(input);
        String format = reader.getFormatName();
        BufferedImage image;

        if ("JPEG".equalsIgnoreCase(format) || "JPG".equalsIgnoreCase(format)) {
            try {
                // 尝试读取图片 (包括颜色的转换).
                image = reader.read(0); //RGB
            } catch (IIOException e) {
                // 读取Raster (没有颜色的转换).
                Raster raster = reader.readRaster(0, null);//CMYK
                image = createJPEG4(raster);
            }
            image.getGraphics().drawImage(image, 0, 0, null);
            //String newfilename = filename.substring(0,filename.lastIndexOf("."))+"_rgb"+filename.substring(filename.lastIndexOf("."));
            String newfilename = filename;
            File newFile = new File(newfilename);
            FileOutputStream out = new FileOutputStream(newFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.flush();
            out.close();

        }
    }

    private static BufferedImage createJPEG4(Raster raster) {
        int w = raster.getWidth();
        int h = raster.getHeight();
        byte[] rgb = new byte[w * h * 3];
        //彩色空间转换
        float[] Y = raster.getSamples(0, 0, w, h, 0, (float[]) null);
        float[] Cb = raster.getSamples(0, 0, w, h, 1, (float[]) null);
        float[] Cr = raster.getSamples(0, 0, w, h, 2, (float[]) null);
        float[] K = raster.getSamples(0, 0, w, h, 3, (float[]) null);
        for (int i = 0, imax = Y.length, base = 0; i < imax; i++, base += 3) {
            float k = 220 - K[i], y = 255 - Y[i], cb = 255 - Cb[i],
                    cr = 255 - Cr[i];

            double val = y + 1.402 * (cr - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);

            val = y - 0.34414 * (cb - 128) - 0.71414 * (cr - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base + 1] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);

            val = y + 1.772 * (cb - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base + 2] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);
        }
        raster = Raster.createInterleavedRaster(new DataBufferByte(rgb, rgb.length), w, h, w * 3, 3, new int[]{0, 1, 2}, null);
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorModel cm = new ComponentColorModel(cs, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        return new BufferedImage(cm, (WritableRaster) raster, true, null);
    }

    //判断是rgb还是cmyk
    public static boolean isRgbOrCmyk(String filename) throws IOException {
        File file = new File(filename);
        boolean isRgb = true;//true是Rgb否则是Cmyk
        //创建输入流
        ImageInputStream input = ImageIO.createImageInputStream(file);
        Iterator readers = ImageIO.getImageReaders(input);
        if (readers == null || !readers.hasNext()) {
            throw new RuntimeException("No ImageReaders found");
        }
        ImageReader reader = (ImageReader) readers.next();
        reader.setInput(input);
        try {
            // 尝试读取图片 (包括颜色的转换).
            reader.read(0); // RGB
            isRgb = true;
        } catch (IIOException e) {
            // 读取Raster (没有颜色的转换).
            reader.readRaster(0, null);// CMYK
            isRgb = false;
        }
        return isRgb;
    }

    // 保存上传的照片
    public static PicStore uploadImage(HttpServletRequest request, PicStoreService picStoreService, String prefix,
                                       String parameterName, String picName, PicStore picToDelete) {
        try {
            MultiPartRequestWrapper multipartRequest = (MultiPartRequestWrapper) request;
            File newHead = multipartRequest.getFiles(parameterName)[0];
            String cusHeadImageName = multipartRequest.getFileNames(parameterName)[0];
            String path = "\\lesson_resources\\image\\" + prefix  + "\\" + DateUtils.nowDate();
            String headImage = ImageUtils.save(newHead, WebApplication.getContextPath() + path, cusHeadImageName, UUID.randomUUID().toString(), null);
            PicStore envPic = new PicStore();
            envPic.setPicName(picName);
            String url = "\\LessonProject" + path + "\\" + headImage;
            String toPath = WebApplication.getContextPath() + path;
            String smallImagestr = "small" + headImage;
            //缩略图前缀加上small
            ImageUtils.writeHighQuality(ImageUtils.zoomImage(toPath + "\\" + headImage), toPath + "\\" + smallImagestr);
            url = url.replaceAll("\\\\", "/");
            request.setAttribute("url", url);//设置图片的URL
            path = path.replaceAll("\\\\", "/");
            envPic.setUrl("/LessonProject" + path + "/" + smallImagestr);
            envPic.setOriUrl(url);
            envPic = picStoreService.getById(picStoreService.save(envPic));
            //如果存在已上传的头像则先删除
            if (picToDelete != null && picToDelete.getId() > Const.IMG_DEFAULT_NUMBER) {
                deleteImage(picStoreService, picToDelete);
            }
            return envPic;
        } catch (Exception e) {
            return null;
        }
    }

    // 删除指定PicStore对应的图片
    public static boolean deleteImage(PicStoreService picStoreService, PicStore picToDelete) {
        try {
            int index = WebApplication.getContextPath().lastIndexOf("\\");
            String sPath = WebApplication.getContextPath().substring(0, index) + picToDelete.getUrl();
            FileOperationUtils.deleteFile(sPath);
            sPath = WebApplication.getContextPath().substring(0, index) + picToDelete.getOriUrl();
            FileOperationUtils.deleteFile(sPath);
            picStoreService.delete(picToDelete.getId());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
