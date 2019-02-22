<%@page import="java.awt.geom.AffineTransform"%>
<%@page import="java.awt.geom.GeneralPath"%>
<%@ page contentType="image/jpeg"
	import="java.util.*,java.awt.*,java.io.*,java.awt.image.*,javax.imageio.*,com.sun.image.codec.jpeg.JPEGCodec,com.sun.image.codec.jpeg.JPEGImageEncoder"
	pageEncoding="utf-8"%>
<%! 
	Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
		
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	private static int[] getRandomRgb() {  
        int[] rgb = new int[3];  
        for (int i = 0; i < 3; i++) {  
            rgb[i] = random.nextInt(255);  
        }  
        return rgb;  
    }  
  	
  	private int getRandomIntColor() {  
        int[] rgb = getRandomRgb();  
        int color = 0;  
        for (int c : rgb) {  
            color = color << 8;  
            color = color | c;  
        }  
        return color;  
    }
  	
    private void shear(Graphics g, int w1, int h1, Color color) {  
        shearX(g, w1, h1, color);  
        shearY(g, w1, h1, color);  
    }  
      
    private void shearX(Graphics g, int w1, int h1, Color color) {  
  
        int period = random.nextInt(2);  
  
        boolean borderGap = true;  
        int frames = 1;  
        int phase = random.nextInt(2);  
  
        for (int i = 0; i < h1; i++) {  
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)  
                            / (double) frames);  
            g.copyArea(0, i, w1, 1, (int) d, 0);  
            if (borderGap) {  
                g.setColor(color);  
                g.drawLine((int) d, i, 0, i);  
                g.drawLine((int) d + w1, i, w1, i);  
            }  
        }  
  
    }  
  
    private void shearY(Graphics g, int w1, int h1, Color color) {  
  
        int period = random.nextInt(40) + 10; // 50;  
  
        boolean borderGap = true;  
        int frames = 20;  
        int phase = 7;  
        for (int i = 0; i < w1; i++) {  
            double d = (double) (period >> 1)  
                    * Math.sin((double) i / (double) period  
                            + (6.2831853071795862D * (double) phase)  
                            / (double) frames);  
            g.copyArea(i, 0, 1, h1, 0, (int) d);  
            if (borderGap) {  
                g.setColor(color);  
                g.drawLine(i, (int) d, i, 0);  
                g.drawLine(i, (int) d + h1, i, h1);  
            }  
        }
       }
       
	//使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符  
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";  
    private static Random random = new Random();  
  
  
    /** 
     * 使用系统默认字符源生成验证码 
     * @param verifySize    验证码长度 
     * @return 
     */  
    public String generateVerifyCode(int verifySize){  
        return generateVerifyCode(verifySize, VERIFY_CODES);  
    }  
    /** 
     * 使用指定源生成验证码 
     * @param verifySize    验证码长度 
     * @param sources   验证码字符源 
     * @return 
     */  
    public String generateVerifyCode(int verifySize, String sources){  
        if(sources == null || sources.length() == 0){  
            sources = VERIFY_CODES;  
        }  
        int codesLen = sources.length();  
        Random rand = new Random(System.currentTimeMillis());  
        StringBuilder verifyCode = new StringBuilder(verifySize);  
        for(int i = 0; i < verifySize; i++){  
            verifyCode.append(sources.charAt(rand.nextInt(codesLen)));
        }  
        return verifyCode.toString();  
    } 
	%>
<%
	//设置页面不缓存
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	// 在内存中创建图象
	int width = 86, height = 34;
	BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
	
	//验证码位数
	int verifySize = 4;
	
	// 获取图形上下文
	Graphics2D g2 = image.createGraphics();
	
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);  
	/* Color[] colors = new Color[5];  
    Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN,  
    	Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,  
        Color.PINK, Color.YELLOW };  
    float[] fractions = new float[colors.length];  
    for(int i = 0; i < colors.length; i++){  
    	colors[i] = colorSpaces[random.nextInt(colorSpaces.length)];  
    	fractions[i] = random.nextFloat();  
    }  
    Arrays.sort(fractions);   */
    // 设置背景色  
    Color c = Color.WHITE;  
    g2.setColor(c);
    g2.fillRect(0, 2, width, height);  
          
    //绘制干扰线  
    g2.setColor(getRandColor(160, 250));// 设置线条的颜色  
    for (int i = 0; i < 20; i++) {  
    	int x = random.nextInt(width - 1);  
        int y = random.nextInt(height - 1);  
        int xl = random.nextInt(6) + 1;  
        int yl = random.nextInt(12) + 1;  
        g2.drawLine(x, y, x + xl + 40, y + yl + 20);  
    }  
        
	// 添加噪点  
	float yawpRate = 0.05f;// 噪声率  
	int area = (int) (yawpRate * width * height);
	for (int i = 0; i < area; i++) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int rgb = getRandomIntColor();
		image.setRGB(x, y, rgb);
	}

	// 使图片扭曲
	shear(g2, width, height, c);  
	
	//产生随机验证码
	g2.setColor(Color.blue);
	int fontSize = height - 4;
	Font font = new Font("Algerian", Font.ITALIC, fontSize);
	g2.setFont(font);
	String verifyCode = generateVerifyCode(4);
	char[] chars = verifyCode.toCharArray();
	for (int i = 0; i < verifySize; i++) {
		AffineTransform affine = new AffineTransform();
		affine.setToRotation(Math.PI / 4 * random.nextDouble()
				* (random.nextBoolean() ? 1 : -1), (width / verifySize)
				* i + fontSize / 2, height / 2);
		g2.setTransform(affine);
		g2.drawChars(chars, i, 1, ((width - 10) / verifySize) * i + 5,
				height / 2 + fontSize / 2 - 10);
	}

	//设定字体
	//g.setFont(new Font("Times New Roman", Font.PLAIN, 22));

	//画边框
	//g.setColor(new Color());
	//g.drawRect(0,0,width-1,height-1);

	// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
	//g.setColor(getRandColor(160, 200));
	/* for (int i = 0; i < 40; i++) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x, y, x + xl, y + yl);
	} */

	// 取随机产生的认证码(4位数字)
	/* String sRand = "";
	for (int i = 0; i < 4; i++) {
		String rand = String.valueOf(random.nextInt(10));
		sRand += rand;
		// 将认证码显示到图象中
		g.setColor(
		//new Color(20 + random.nextInt(110), 20 + random
		//		.nextInt(110), 20 + random.nextInt(110))
		Color.blue
				);// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		g.drawString(rand, 13 * i + 6, 16);
	} */

	// 将认证码存入SESSION
	session.setAttribute("rand", verifyCode);

	// 图象生效
	g2.dispose();
	OutputStream output = response.getOutputStream();

	// 输出图象到页面
	System.out.println(response.getOutputStream());
	System.out.println(image);
	//ImageIO.write(image, "JPEG", response.getOutputStream());
	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response
			.getOutputStream());
	encoder.encode(image);
	output.flush();
	out.clear();
	out = pageContext.pushBody();
%>
