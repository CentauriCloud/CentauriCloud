package org.centauri.cloud.daemon.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketPing;
import org.centauri.cloud.common.network.packets.PacketServerRegister;
import org.centauri.cloud.common.network.packets.PacketTemplateData;
import org.centauri.cloud.common.network.server.ServerType;
import org.centauri.cloud.daemon.Daemon;

@Log4j2
public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {
	
	@SneakyThrows
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if(packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		} else if(packet instanceof PacketTemplateData) {
			PacketTemplateData templateData = (PacketTemplateData) packet;
			this.log.info("Received template: {} Size: {} kb", templateData.getTemplateName(), templateData.getTemplateData().length / 1028.0);
			FileUtils.deleteDirectory(new File("templates/" + templateData.getTemplateName()));
			this.unzip(templateData.getTemplateData(), "templates/" + templateData.getTemplateName());
			this.log.info("Successfully unzipped!");
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new PacketServerRegister(Daemon.getInstance().getCloudConfiguration().getPrefix(), ServerType.DAEMON, -1));
	}

	@SneakyThrows
	private void unzip(byte[] data, String outputFolder) {
		byte[] buffer = new byte[1024];
		
		File folder = new File(outputFolder);
		if (!folder.exists()) {
			folder.mkdir();
		}

		ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(data));
		ZipEntry ze = zis.getNextEntry();

		while (ze != null) {

			String fileName = ze.getName();
			File newFile = new File(outputFolder+ "/" +fileName);

			new File(newFile.getParent()).mkdirs();

			if(!newFile.exists())
				newFile.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(newFile);

			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			fos.close();
			ze = zis.getNextEntry();
		}

		zis.closeEntry();
		zis.close();
	}
	
}
