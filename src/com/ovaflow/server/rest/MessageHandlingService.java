package com.ovaflow.server.rest;

import java.io.File;
import java.util.Vector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.ovaflow.server.dao.AccountInterface;
import com.ovaflow.server.dao.AvatarInterface;
import com.ovaflow.server.dao.BeatMapInterface;
import com.ovaflow.server.dao.ScoreInterface;
import com.ovaflow.server.dao.SongInterface;
import com.ovaflow.server.dao.impl.AccountManager;
import com.ovaflow.server.dao.impl.AvatarManager;
import com.ovaflow.server.dao.impl.BeatMapManager;
import com.ovaflow.server.dao.impl.ScoreManager;
import com.ovaflow.server.dao.impl.SongManager;
import com.ovaflow.server.dto.Account;
import com.ovaflow.server.dto.Avatar;
import com.ovaflow.server.dto.BeatMap;
import com.ovaflow.server.dto.Score;
import com.ovaflow.server.dto.Song;
import com.ovaflow.server.token.Tokentable;
@Path("/ovf")
public class MessageHandlingService {
	
	private static Tokentable tt = new Tokentable();
	
	@GET
	@Path("/newuser")
	public Response newuserinfo(@QueryParam("usra") String useraccount,
			@QueryParam("usrn") String username,
			@QueryParam("pass")  String password) {
		AccountInterface ac = new AccountManager();
		
		int newmoney = ac.registerNewAccount(useraccount,username, password);
		String output = "{" + '"' + "User" + '"' + ":" + '"' +username + '"' +","+'"' +"State"+'"' +": "  +newmoney+"}";
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/acinfo")
	public Response getAccountInfo(@QueryParam("usr") String username,
			@QueryParam("pass")  String password) {
		AccountInterface ac = new AccountManager();
		Account account = ac.fetchAccountInfo(username, password);
		String x = "";
		if(tt.addToken(account) != null)
		{
			x = tt.addToken(account);
		}
		String output = "{" + '"' + "Token"+'"' +":"+'"' + x+'"' +","+'"' +"User"+'"' +":" +'"' + account.getUsername() +'"' + ","+'"' +"RMB"+'"' +":" + account.getRMB() + ","+'"' +"Current Avatar"+'"' +": " + account.getCA()+"}";
		return Response.status(200).entity(output).build();
	}
	@GET
	@Path("/rrmb")
	public Response renewRMB(@QueryParam("usr") String username,
			@QueryParam("$")  String money) {
		String output = "";
		AccountInterface ac = new AccountManager();
		Account user = tt.getAccount(username);
		if (user != null) {
			int newmoney = ac.renewRMB(user.getUsername(), money);
			output = "{" + '"' +"User"+ '"' +":" + '"' + user.getUsername() + '"' + ","+ '"' +"RMB"+ '"' +": " + newmoney+"}";
			return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/newhs")
	public Response newhighscorefo(@QueryParam("usr") String username,
			@QueryParam("bm")  int bmid,@QueryParam("score")  int score,@QueryParam("combo")  int combo) {
		ScoreInterface hs = new ScoreManager();
		Account user = tt.getAccount(username);
		if (user != null) {
			int s = hs.newhighscore(user.getUsername(), bmid,score,combo);
			String output = "{" + '"' +"User"+ '"' +":" + '"' + user.getUsername()+ '"' + ","+ '"' +"BMId"+ '"' +": " + bmid
							+ ","+ '"' +"State"+ '"' +": " + s+"}";
			return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/hsinfo")
	public Response getHighscoreInfo(@QueryParam("usr") String username,
			@QueryParam("bm")  int bmid) {
		ScoreInterface hs = new ScoreManager();
		Account user = tt.getAccount(username);
		if (user != null) {
			Score score = hs.gethighscore(user.getUsername(), bmid);
			String output = "{" + '"'+"User"+ '"'+":" + '"'+score.getUsername() + '"'+ ","+ '"'+"BMId"+ '"'+": " + score.getBmid()
							+ ","+ '"'+"Score"+ '"'+": " + score.getScore()+ ","+ '"'+"Combo"+ '"'+": " + score.getCombo()+"}";
			
			return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/sbinfo")
	public Response getScoreboardInfo(@QueryParam("bm")  int bmid) {
		ScoreInterface hs = new ScoreManager();
		Vector<Score> score = hs.scorebarod(bmid);
		String output = "{"+ '"'+"ScoreBoard"+ '"'+":[";
		for(int i=0;i<score.size();i++)
		{
		output = output + "{" + '"'+"User"+ '"'+":" + '"'+ score.elementAt(i).getUsername() + '"'+","+ '"'+"BMId"+ '"'+": " + score.elementAt(i).getBmid()
						+ ","+ '"'+"Score"+ '"'+": " + score.elementAt(i).getScore()+ ","+ '"'+"Combo"+ '"'+": " + score.elementAt(i).getCombo()
						+"},";
		}
		output = output.substring(0,output.length()-1) + "]}";
		return Response.status(200).entity(output).build();
	}
	
	
	@GET
	@Path("/owna")
	public Response getOwnAvatar(@QueryParam("usr")  String UserName) {
		AvatarInterface a = new AvatarManager();
		Account user = tt.getAccount(UserName);
		int flag = 0;
		if(user!=null)
		{
			
			Vector<Avatar> avatar = a.OwnAvatar(user.getUsername());
			String output = "{"+ '"'+"AvatarList"+ '"'+":[";
			for(int i=0;i<avatar.size();i++)
			{
				flag = 1;
			output = output +"{" + '"'+"AvatarId"+ '"'+":" + avatar.elementAt(i).getID() + ","+ '"'+"Avatar Name"+ '"'+": " + '"'+avatar.elementAt(i).getName()
					+ '"'+ ","+ '"'+"Price"+ '"'+": " + avatar.elementAt(i).getPrice()+"},";
			}
			if(flag != 0){
				output = output.substring(0,output.length()-1);
			}
			
			output = output  + "]}";
			
			return Response.status(200).entity(output).build();
		}
		
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/nowna")
	public Response getNotOwnAvatar(@QueryParam("usr")  String UserName) {
		AvatarInterface a = new AvatarManager();
		Account user = tt.getAccount(UserName);
		int flag = 0;
		if(user!=null)
		{
			
			Vector<Avatar> avatar = a.NotOwnAvatar(user.getUsername());
			String output = "{"+ '"'+"AvatarList"+ '"'+":[";
			for(int i=0;i<avatar.size();i++)
			{
				flag = 1;
			output = output +"{" + '"'+"AvatarId"+ '"'+":" + avatar.elementAt(i).getID() + ","+ '"'+"Avatar Name"+ '"'+": " + '"'+avatar.elementAt(i).getName()
					+ '"'+ ","+ '"'+"Price"+ '"'+": " + avatar.elementAt(i).getPrice()+"},";
			}
			if(flag != 0){
				output = output.substring(0,output.length()-1);
			}
			
			output = output  + "]}";
			
			return Response.status(200).entity(output).build();
		}
		
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/changea")
	public Response changeavatar(@QueryParam("usr") String username,
			@QueryParam("ai")  int ai) {
		AvatarInterface a = new AvatarManager();
		Account user = tt.getAccount(username);
		if(user!=null)
		{
			int s = a.ChangeAvatar(user.getUsername(), ai);
			String output = "{" + '"'+"User"+ '"'+":" + user.getUsername() 
							+ ","+ '"'+"State"+ '"'+": " + s+"}";
			return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/buya")
	public Response buyavatar(@QueryParam("usr") String username,
			@QueryParam("ai")  int ai,@QueryParam("$") int price,@QueryParam("RMB") int RMB ) {
		AvatarInterface a = new AvatarManager();
		Account user = tt.getAccount(username);
		if(user!=null)
		{
			int s = a.BuyAvatar(user.getUsername(), ai, price,RMB);
			String output = "{" + '"'+"User"+ '"'+":" + user.getUsername() 
							+ ","+ '"'+"State"+ '"'+": " + s+"}";
			return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	
	
	@GET
	@Path("/song")
	public Response getSong() {
		SongInterface hs = new SongManager();
		Vector<Song> score = hs.AllSong();
		String output = "{"+ '"'+"SongList"+ '"'+":[";
		for(int i=0;i<score.size();i++)
		{
		output = output +"{" + '"'+"Id"+ '"'+":" + score.elementAt(i).getSongId() + ","+ '"'+"SongName"+ '"'+": " + '"'+ score.elementAt(i).getSongName()
				+ '"'+ ","+ '"'+"Singer"+ '"'+": " + '"'+ score.elementAt(i).getSinger()+ '"'+ "},";
		}
		output = output.substring(0,output.length()-1)+"]}";
		return Response.status(200).entity(output).build();
	}
	
		
	@GET
	@Path("/sbys")
	public Response getOwnSongS(@QueryParam("singer")  String singer) {
		SongInterface hs = new SongManager();
		int flag = 0;
			Vector<Song> score = hs.SearchS(singer);
			String output = "{"+ '"'+"SongList"+ '"'+":[";
			for(int i=0;i<score.size();i++)
			{
				flag = 1;
			output = output +"{" + '"'+"Id"+ '"'+":" + score.elementAt(i).getSongId() + ","+ '"'+"SongName"+ '"'+": " + '"'+ score.elementAt(i).getSongName()
					+ '"'+ ","+ '"'+"Singer"+ '"'+": " + '"'+ score.elementAt(i).getSinger()+ '"'+ "},";
			}
			if(flag != 0)
			{output = output.substring(0,output.length()-1);}
			output = output +"]}";
			return Response.status(200).entity(output).build();
	}
	
		
	@GET
	@Path("/sbyn")
	public Response getOwnSongN(@QueryParam("songname")  String SongName) {
		SongInterface hs = new SongManager();
			int flag = 0;
			Vector<Song> score = hs.SearchN(SongName);
			String output = "{"+ '"'+"SongList"+ '"'+":[";
			for(int i=0;i<score.size();i++)
			{
				flag = 1;
			output = output +"{" + '"'+"Id"+ '"'+":" + score.elementAt(i).getSongId() + ","+ '"'+"SongName"+ '"'+": " + '"'+ score.elementAt(i).getSongName()
					+ '"'+ ","+ '"'+"Singer"+ '"'+": " + '"'+ score.elementAt(i).getSinger()+ '"'+ "},";
			}
			if(flag != 0)
			{output = output.substring(0,output.length()-1);}
			output = output +"]}";
			return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/haves")
	public Response havesong(@QueryParam("usr")  String UserName,@QueryParam("id")  int SongId) {
		SongInterface hs = new SongManager();
		Account user = tt.getAccount(UserName);
		if(user!=null)
		{
			int score = hs.haveSong(user.getUsername(),SongId);
			return Response.status(200).entity("{"+'"'+"State" +'"' + ":" +score+"}").build();
		}
		return Response.status(200).entity("{"+'"'+"State" +'"' + ":" +"-1"+"}").build();
	}
	
	@GET
	@Path("/downloadsong")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getSong(@QueryParam("usr") String UserName, @QueryParam("id") int SongId) throws Exception
	{
		
				Account user = tt.getAccount(UserName);
				if(user!=null)
				{
					SongInterface s = new SongManager();
					String addr = s.SongInfo(SongId);
					File file = new File(addr);
					ResponseBuilder response = Response.ok((Object) file);
					response.header("Content-Disposition", "attachment;filename=Song"+SongId+addr.substring(addr.lastIndexOf("."),addr.length()));
					SongInterface hs = new SongManager();
					hs.addSong(user.getUsername(),SongId);
					return response.build();
				}
				
				return null;
				
}
	
	

	
	@GET
	@Path("/obm")
	public Response getOBM(@QueryParam("usr")  String UserName) {
		Account user = tt.getAccount(UserName);
		if(user!=null)
		{
		BeatMapInterface hs = new BeatMapManager();
		Vector<BeatMap> score = hs.OwnBeatMap(user.getUsername());
		String output = "{"+ '"'+"BeatMapList"+ '"'+":[";
		int flag = 0;
		for(int i=0;i<score.size();i++)
		{
			flag = 1;
		output = output +"{" + '"'+"BMId"+ '"'+":" + score.elementAt(i).getBMId() + ","+ '"'+"SongId"+ '"'+": " + '"'+ score.elementAt(i).getSongId()
				+ '"'+ ","+ '"'+"BMName"+ '"'+": " + '"'+ score.elementAt(i).getBMName()+ ","+ '"'+"Creater"+ '"'+": " + '"'+ score.elementAt(i).getCreater()+ '"'+ "},";
		}
		if(flag != 0)
		{output = output.substring(0,output.length()-1);}
		output = output +"]}";
		return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/nobm")
	public Response getNOBM(@QueryParam("usr")  String UserName) {
		Account user = tt.getAccount(UserName);
		if(user!=null)
		{
		BeatMapInterface hs = new BeatMapManager();
		Vector<BeatMap> score = hs.NotOwnBeatMap(user.getUsername());
		String output = "{"+ '"'+"BeatMapList"+ '"'+":[";
		int flag = 0;
		for(int i=0;i<score.size();i++)
		{
			flag = 1;
		output = output +"{" + '"'+"BMId"+ '"'+":" + score.elementAt(i).getBMId() + ","+ '"'+"SongId"+ '"'+": " + '"'+ score.elementAt(i).getSongId()
				+ '"'+ ","+ '"'+"BMName"+ '"'+": " + '"'+ score.elementAt(i).getBMName()+ ","+ '"'+"Creater"+ '"'+": " + '"'+ score.elementAt(i).getCreater()+ '"'+ "},";
		}
		if(flag != 0)
		{output = output.substring(0,output.length()-1);}
		output = output +"]}";
		return Response.status(200).entity(output).build();
		}
	return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/oscbm")
	public Response getOSCBM(@QueryParam("usr")  String UserName, @QueryParam("creater")  String Creater) {
		Account user = tt.getAccount(UserName);
		if(user!=null)
		{
		BeatMapInterface hs = new BeatMapManager();
		Vector<BeatMap> score = hs.OwnSearchC(user.getUsername(),Creater);
		String output = "{"+ '"'+"BeatMapList"+ '"'+":[";
		int flag = 0;
		for(int i=0;i<score.size();i++)
		{
			flag = 1;
		output = output +"{" + '"'+"BMId"+ '"'+":" + score.elementAt(i).getBMId() + ","+ '"'+"SongId"+ '"'+": " + '"'+ score.elementAt(i).getSongId()
				+ '"'+ ","+ '"'+"BMName"+ '"'+": " + '"'+ score.elementAt(i).getBMName()+ ","+ '"'+"Creater"+ '"'+": " + '"'+ score.elementAt(i).getCreater()+ '"'+ "},";
		}
		if(flag != 0)
		{output = output.substring(0,output.length()-1);}
		output = output +"]}";
		return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/noscbm")
	public Response getNOSCBM(@QueryParam("usr")  String UserName, @QueryParam("creater")  String Creater) {
		Account user = tt.getAccount(UserName);
		if(user!=null)
		{
		BeatMapInterface hs = new BeatMapManager();
		Vector<BeatMap> score = hs.NotOwnSearchC(user.getUsername(),Creater);
		String output = "{"+ '"'+"BeatMapList"+ '"'+":[";
		int flag = 0;
		for(int i=0;i<score.size();i++)
		{
			flag = 1;
		output = output +"{" + '"'+"BMId"+ '"'+":" + score.elementAt(i).getBMId() + ","+ '"'+"SongId"+ '"'+": " + '"'+ score.elementAt(i).getSongId()
				+ '"'+ ","+ '"'+"BMName"+ '"'+": " + '"'+ score.elementAt(i).getBMName()+ ","+ '"'+"Creater"+ '"'+": " + '"'+ score.elementAt(i).getCreater()+ '"'+ "},";
		}
		if(flag != 0)
		{output = output.substring(0,output.length()-1);}
		output = output +"]}";
		return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/osnbm")
	public Response getOSNBM(@QueryParam("usr")  String UserName, @QueryParam("beatmapname")  String BeatMapName) {
		Account user = tt.getAccount(UserName);
		if(user!=null)
		{
		BeatMapInterface hs = new BeatMapManager();
		Vector<BeatMap> score = hs.OwnSearchN(user.getUsername(),BeatMapName);
		String output = "{"+ '"'+"BeatMapList"+ '"'+":[";
		int flag = 0;
		for(int i=0;i<score.size();i++)
		{
			flag = 1;
		output = output +"{" + '"'+"BMId"+ '"'+":" + score.elementAt(i).getBMId() + ","+ '"'+"SongId"+ '"'+": " + '"'+ score.elementAt(i).getSongId()
				+ '"'+ ","+ '"'+"BMName"+ '"'+": " + '"'+ score.elementAt(i).getBMName()+ ","+ '"'+"Creater"+ '"'+": " + '"'+ score.elementAt(i).getCreater()+ '"'+ "},";
		}
		if(flag != 0)
		{output = output.substring(0,output.length()-1);}
		output = output +"]}";
		return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("/nosnbm")
	public Response getNOSNBM(@QueryParam("usr")  String UserName, @QueryParam("beatmapname")  String BeatMapName) {
		Account user = tt.getAccount(UserName);
		if(user!=null)
		{
		BeatMapInterface hs = new BeatMapManager();
		Vector<BeatMap> score = hs.NotOwnSearchN(user.getUsername(),BeatMapName);
		String output = "{"+ '"'+"BeatMapList"+ '"'+":[";
		int flag = 0;
		for(int i=0;i<score.size();i++)
		{
			flag = 1;
		output = output +"{" + '"'+"BMId"+ '"'+":" + score.elementAt(i).getBMId() + ","+ '"'+"SongId"+ '"'+": " + '"'+ score.elementAt(i).getSongId()
				+ '"'+ ","+ '"'+"BMName"+ '"'+": " + '"'+ score.elementAt(i).getBMName()+ ","+ '"'+"Creater"+ '"'+": " + '"'+ score.elementAt(i).getCreater()+ '"'+ "},";
		}
		if(flag != 0)
		{output = output.substring(0,output.length()-1);}
		output = output +"]}";
		return Response.status(200).entity(output).build();
		}
		return Response.status(200).entity("").build();
	}
	
	
	
	@GET
	@Path("/downloadbm")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getBM(@QueryParam("usr") String UserName, @QueryParam("id") int BMId) throws Exception
	{
		
				Account user = tt.getAccount(UserName);
				if(user!=null)
				{
					BeatMapInterface s = new BeatMapManager();
					String addr = s.BeatMapInfo(BMId).getPoniter();
					File file = new File(addr);
					ResponseBuilder response = Response.ok((Object) file);
					response.header("Content-Disposition", "attachment;filename=BeatMap"+BMId+addr.substring(addr.lastIndexOf("."),addr.length()));
					BeatMapInterface hs = new BeatMapManager();
					hs.AddBM(user.getUsername(),BMId);
					return response.build();
				}
				
				return null;
				
}
	
	
}
