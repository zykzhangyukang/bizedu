import com.coderman.service.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 10:28
 */
@Slf4j
public class FfmepgTest {

    public static void main(String[] args) throws IOException {

        // 分片加密信息路径
        String keyInfoPath = "E:\\workspace\\bizedu\\tools\\key_info_file.keyinfo";

        // 初始化FFmpeg
        FFmpeg ffmpeg = new FFmpeg("E:\\workspace\\bizedu\\tools\\ffmpeg-win.exe");
        FFprobe ffprobe = new FFprobe("E:\\workspace\\bizedu\\tools\\ffprobe-win.exe");
        // 输入视频文件路径
        String inputVideoPath = "C:\\Users\\Administrator\\Desktop\\test\\input.mp4";
        // 输出M3U8文件路径
        String outputM3U8Path = "C:\\Users\\Administrator\\Desktop\\test\\output\\output.m3u8";
        // 视频封面
        String outputImagePath = "C:\\Users\\Administrator\\Desktop\\test\\output\\output_image.jpg"; // 输出图像文件路径


        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        FFmpegProbeResult in = ffprobe.probe(inputVideoPath);
        FFmpegFormat format = in.getFormat();

        //【mp4->m3u8】 ffmpeg -i C:\Users\Administrator\Desktop\test\input.mp4 -codec: copy  -start_number 0 -hls_time 10 -hls_list_size 0 -f hls C:\Users\Administrator\Desktop\test\output\output.m3u8
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputVideoPath)
                .addOutput(outputM3U8Path)
                .setFormat("hls")
                .addExtraArgs("-codec:", "copy")
                .addExtraArgs("-start_number", "0")
//                .addExtraArgs("-hls_key_info_file" , keyInfoPath)
                .addExtraArgs("-hls_time", "10")
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-hls_segment_filename" , "C:\\Users\\Administrator\\Desktop\\test\\output\\segment_%d.ts")
                .done();

        // 【截取封面】指定要截取的时间点（这里截取视频中间的一帧）
        long durationMillis = (long) (format.duration * 1000);
        long captureTimeMillis = durationMillis / 2;
        FFmpegBuilder builder2 = new FFmpegBuilder()
                .setInput(inputVideoPath)
                .addOutput(outputImagePath)
                .addExtraArgs("-ss", String.valueOf(captureTimeMillis / 1000)) // 截取时间点
                .addExtraArgs("-vframes", "1") // 截取一帧图像
                .done();

        FFmpegJob fFmpegJob = null;
        try {
            fFmpegJob = executor.createJob(builder, new ProgressListener() {
                final double duration_ns = format.duration * TimeUnit.SECONDS.toNanos(1);
                @Override
                public void progress(Progress progress) {
                    double percentage = progress.out_time_ns / duration_ns;
                    // Print out interesting information about the progress
                    System.out.println(Thread.currentThread().getName() + "-----"+format.duration+"s------>" + String.format(
                            "[%.0f%%] status:%s frame:%d time:%s ms fps:%.0f speed:%.2fx",
                            percentage * 100,
                            progress.status,
                            progress.frame,
                            FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                            progress.fps.doubleValue(),
                            progress.speed
                    ));
                }
            });
            fFmpegJob.run();
        }catch (Exception e){
            log.error("视频转码失败！！！！e:{}",e.getMessage(),e);
        }

        Assert.notNull(fFmpegJob , "fFmpegJob is null");
        if(Objects.equals(FFmpegJob.State.FAILED, fFmpegJob.getState())){
            log.error("失败！！！");
        }else if (Objects.equals(FFmpegJob.State.FINISHED, fFmpegJob.getState())){
            log.info("成功！！！");
        }


    }

}