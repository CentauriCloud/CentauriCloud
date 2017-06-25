package org.centauri.cloud.daemon.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.centauri.cloud.common.network.packets.Packet;
import org.centauri.cloud.common.network.packets.PacketKillServer;
import org.centauri.cloud.common.network.packets.PacketPing;
import org.centauri.cloud.common.network.packets.PacketServerRegister;
import org.centauri.cloud.common.network.packets.PacketStartServer;
import org.centauri.cloud.common.network.packets.PacketTemplateData;
import org.centauri.cloud.common.network.server.ServerType;
import org.centauri.cloud.daemon.Daemon;
import org.centauri.cloud.daemon.server.Template;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Log4j2
public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {

	@SneakyThrows
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		if (packet instanceof PacketPing) {
			ctx.channel().writeAndFlush(packet);
		} else if (packet instanceof PacketTemplateData) {

			PacketTemplateData templateData = (PacketTemplateData) packet;
			this.log.info("Received template: {} Size: {} kb", templateData.getTemplateName(), templateData.getTemplateData().length / 1028.0);

			FileUtils.deleteDirectory(new File("templates/" + templateData.getTemplateName()));

			this.unzip(templateData.getTemplateData(), "templates/" + templateData.getTemplateName());

			this.log.info("Successfully unzipped!");
			Daemon.getInstance().getServerManager().getTemplates().add(new Template(templateData.getTemplateName()));

		} else if (packet instanceof PacketStartServer) {

			PacketStartServer startServer = (PacketStartServer) packet;
			Daemon.getInstance().getServerManager().startServer(startServer.getTemplateName());

		} else if (packet instanceof PacketKillServer) {
			System.exit(0);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new PacketServerRegister(Daemon.getInstance().getCloudConfiguration().getPrefix(), ServerType.DAEMON, -1));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			ctx.close();
			this.log.warn("Channel closed with message: {}", cause.getMessage());
		} else {
			this.log.catching(cause);
		}
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
			File newFile = new File(outputFolder + "/" + fileName);

			new File(newFile.getParent()).mkdirs();

			if (!newFile.exists())
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
