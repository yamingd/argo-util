package com.argo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * ip工具类，用于处理获取apache代理后的真实IP
 * @author yaming_deng
 *
 */
public class IpUtil {
	
	protected static Logger logger = LoggerFactory.getLogger(IpUtil.class);

	public static String[] getHostServerIp(){
		try {
			Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
			List<String> ips = new ArrayList<String>();
			while(ifs.hasMoreElements()){
				NetworkInterface ni = (NetworkInterface)ifs.nextElement();
                if (logger.isDebugEnabled()){
                    logger.debug("Net interface: " + ni.getName());
                }
				Enumeration<InetAddress> e2 = ni.getInetAddresses();
				while(e2.hasMoreElements()){
					InetAddress ip = e2.nextElement();
					String ipstr = ip.toString().substring(1);
					if(ip.isLoopbackAddress() || ipstr.startsWith("127.0.0.1") || ipstr.indexOf(":")>0){
						continue;
					}
					ips.add(ipstr);
                    if (logger.isDebugEnabled()){
                        logger.debug("IP Address: " + ip.toString());
                    }
				}
			}
			if(ips.size()>0){
				return ips.toArray(new String[0]);
			}
		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
