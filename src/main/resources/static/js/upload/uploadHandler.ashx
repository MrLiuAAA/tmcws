<%@ WebHandler Language="C#" Class="upload" %>

using System;
using System.IO;
using System.Web;

public class upload : IHttpHandler {
    
    public void ProcessRequest (HttpContext context) {
        context.Response.ContentType = "text/plain";
        context.Response.Charset = "utf-8";

        HttpPostedFile file = context.Request.Files["Filedata"];
        string uploadPath = context.Server.MapPath("~\\temps\\");
        String thumPath = context.Server.MapPath("~\\temps\\s\\");
        if (file != null)
        {
            if (!Directory.Exists(uploadPath))
            {
                Directory.CreateDirectory(uploadPath);
                Directory.CreateDirectory(thumPath);//创建缩率图文件夹
            }
            if (!Directory.Exists(thumPath))
            {
                Directory.CreateDirectory(thumPath);//创建缩率图文件夹
            }
            String MyFileName = file.FileName;
            string[] myfile = MyFileName.Split('.');               //把文件名与文件类型分开
            string dotname = myfile[myfile.Length - 1].ToString().ToLower();             //得到文件类型
            //上传文件并指定上传目录的路径  
            Random myrdn = new Random();                    //产生随机数
            String vioname = DateTime.Now.ToString("yyMMddHHmmm") + myrdn.Next(10000).ToString() + "." + dotname;
            
            file.SaveAs(uploadPath + vioname);
            //生成缩略图  
            MakeThumbnail(uploadPath + vioname, uploadPath + "s\\" + vioname, 80, 80);
        
            context.Response.Write(vioname);
        }  
    }

    private void MakeThumbnail(string sourcePath, string newPath, int width, int height)
    {
        System.Drawing.Image ig = System.Drawing.Image.FromFile(sourcePath);
        int towidth = width;
        int toheight = height;
        int x = 0;
        int y = 0;
        int ow = ig.Width;
        int oh = ig.Height;
        if ((double)ig.Width / (double)ig.Height > (double)towidth / (double)toheight)
        {
            oh = ig.Height;
            ow = ig.Height * towidth / toheight;
            y = 0;
            x = (ig.Width - ow) / 2;

        }
        else
        {
            ow = ig.Width;
            oh = ig.Width * height / towidth;
            x = 0;
            y = (ig.Height - oh) / 2;
        }
        System.Drawing.Image bitmap = new System.Drawing.Bitmap(towidth, toheight);
        System.Drawing.Graphics g = System.Drawing.Graphics.FromImage(bitmap);
        g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.High;
        g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.HighQuality;
        g.Clear(System.Drawing.Color.Transparent);
        g.DrawImage(ig, new System.Drawing.Rectangle(0, 0, towidth, toheight), new System.Drawing.Rectangle(x, y, ow, oh), System.Drawing.GraphicsUnit.Pixel);
        try
        {
            bitmap.Save(newPath, System.Drawing.Imaging.ImageFormat.Jpeg);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            ig.Dispose();
            bitmap.Dispose();
            g.Dispose();
        }

    }  
    
    public bool IsReusable {
        get {
            return false;
        }
    }

}