package com.ovaflow.server.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ovaflow.server.dao.BeatMapInterface;
import com.ovaflow.server.dto.BeatMap;

public class BeatMapManager implements BeatMapInterface{
    
	
	private int addbm(String UserName, int BMId)
	{
		int flag = 0;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("insert into tab_ubrelation values(?,?)");
			prestate.setString(1, UserName);
			prestate.setInt(2, BMId);
			boolean res = prestate.execute();
			if (!res) {
				flag = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					prestate.close();
					con.close();
				} catch (SQLException ex2) {
				}
			}
		}
		return flag;
	}
    private BeatMap beatmapinfo(int BMId)
    {
        int flag = 0;
        BeatMap answer = null;
        Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
        PreparedStatement prestate = null;
        try {
            prestate = con
                    .prepareStatement("select * from tab_BeatMapInfo where BMId ='"
                            + BMId  + "'");
            ResultSet res = prestate.executeQuery();
            // ResultSetMetaData resd = res.getMetaData();
            while (res.next()) {
                    flag = 1;
                    answer = new BeatMap(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getString(5));
                    break;
            }
            if(flag != 1)
            {
                answer = new BeatMap(BMId,-1,null,null,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    prestate.close();
                    con.close();
                } catch (SQLException ex2) {
                }
            }
        }
        return answer;
    }
    
    private Vector<BeatMap> ownBeatMap(String UserName)
    {
        Vector<BeatMap> los = new Vector<BeatMap>();
        Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
        PreparedStatement prestate = null;
        try {
            prestate = con
                    .prepareStatement("select ai.* from tab_ubrelation as ref  inner join tab_BeatMapinfo as ai on  ref.UserName ='"+ UserName + "'and ai.BMId = ref.BMId");
            ResultSet res = prestate.executeQuery();
            
            // ResultSetMetaData resd = res.getMetaData();
            while (res.next()) {
                BeatMap a = new BeatMap(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getString(5));
                
                los.add(a);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    prestate.close();
                    con.close();
                } catch (SQLException ex2) {
                }
            }
        }
        
        return los;
    }
    
    private Vector<BeatMap> ownsearchc(String UserName, String Creater)
    {
        Vector<BeatMap> los = new Vector<BeatMap>();
        Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
        PreparedStatement prestate = null;
        try {
            prestate = con
                    .prepareStatement("select ai.* from tab_ubrelation as ref  inner join tab_BeatMapinfo as ai on  ref.UserName ='"+ UserName + "'and ai.BMId = ref.BMId and ai.Creater = '"+ Creater + "'");
            ResultSet res = prestate.executeQuery();
            // ResultSetMetaData resd = res.getMetaData();
            while (res.next()) {
                BeatMap a = new BeatMap(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getString(5));
                los.add(a);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    prestate.close();
                    con.close();
                } catch (SQLException ex2) {
                }
            }
        }
        
        return los;
    }
    
    private Vector<BeatMap> ownsearchn(String UserName, String BeatMapName)
    {
        Vector<BeatMap> los = new Vector<BeatMap>();
        Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
        PreparedStatement prestate = null;
        try {
            prestate = con
                    .prepareStatement("select ai.* from tab_ubrelation as ref  inner join tab_BeatMapinfo as ai on  ref.UserName ='"+ UserName + "'and ai.BMId = ref.BMId and ai.BMName = '"+ BeatMapName + "'");
            ResultSet res = prestate.executeQuery();
            // ResultSetMetaData resd = res.getMetaData();
            while (res.next()) {
                BeatMap a = new BeatMap(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getString(5));
                los.add(a);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    prestate.close();
                    con.close();
                } catch (SQLException ex2) {
                }
            }
        }
        
        return los;
    }
    
    private Vector<BeatMap> notownBeatMap(String UserName)
    {
        Vector<BeatMap> los = new Vector<BeatMap>();
        Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
        PreparedStatement prestate = null;
        try {
            prestate = con
                    .prepareStatement("select * from tab_BeatMapinfo as A left join (select ai.* from tab_ubrelation as ref  left join tab_BeatMapinfo as ai on  ref.UserName = '"+ UserName +"'and ai.BMId = ref.BMId) as B using(BMId) where B.BMId is null");
            ResultSet res = prestate.executeQuery();
            // ResultSetMetaData resd = res.getMetaData();
            while (res.next()) {
                BeatMap a = new BeatMap(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getString(5));
                los.add(a);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    prestate.close();
                    con.close();
                } catch (SQLException ex2) {
                }
            }
        }
        
        return los;
    }

    private Vector<BeatMap> notownsearchn(String UserName,String BeatMapName)
    {
        Vector<BeatMap> los = new Vector<BeatMap>();
        Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
        PreparedStatement prestate = null;
        try {
            prestate = con
                    .prepareStatement("select * from tab_BeatMapinfo as A left join (select ai.* from tab_ubrelation as ref  left join tab_BeatMapinfo as ai on  ref.UserName = '"+ UserName +"'and ai.BMId = ref.BMId) as B using(BMId) where B.BMId is null and A.BMName='"+BeatMapName+"'");
            ResultSet res = prestate.executeQuery();
            // ResultSetMetaData resd = res.getMetaData();
            while (res.next()) {
                BeatMap a = new BeatMap(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getString(5));
                los.add(a);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    prestate.close();
                    con.close();
                } catch (SQLException ex2) {
                }
            }
        }
        
        return los;
    }
    
    private Vector<BeatMap> notownsearchc(String UserName,String Creater)
    {
        Vector<BeatMap> los = new Vector<BeatMap>();
        Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
        PreparedStatement prestate = null;
        try {
            prestate = con
                    .prepareStatement("select * from tab_BeatMapinfo as A left join (select ai.* from tab_ubrelation as ref  left join tab_BeatMapinfo as ai on  ref.UserName = '"+ UserName +"'and ai.BMId = ref.BMId) as B using(BMId) where B.BMId is null and A.Creater='"+Creater+"'");
            ResultSet res = prestate.executeQuery();
            // ResultSetMetaData resd = res.getMetaData();
            while (res.next()) {
                BeatMap a = new BeatMap(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getString(5));
                los.add(a);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    prestate.close();
                    con.close();
                } catch (SQLException ex2) {
                }
            }
        }
        
        return los;
    }

    @Override
    public BeatMap BeatMapInfo(int BMId) {
        return beatmapinfo(BMId);
    }

    @Override
    public Vector<BeatMap> OwnBeatMap(String UserName) {
        return ownBeatMap(UserName);
    }

    @Override
    public Vector<BeatMap> OwnSearchC(String UserName, String Creater) {
        return ownsearchc(UserName,Creater);
    }

    @Override
    public Vector<BeatMap> OwnSearchN(String UserName, String BeatMapName) {
        return ownsearchn(UserName,BeatMapName);
    }

    @Override
    public Vector<BeatMap> NotOwnBeatMap(String UserName) {
        return notownBeatMap(UserName);
    }

    @Override
    public Vector<BeatMap> NotOwnSearchC(String UserName, String Creater) {
        return notownsearchc(UserName,Creater);
    }

    @Override
    public Vector<BeatMap> NotOwnSearchN(String UserName, String BeatMapName) {
    	return notownsearchn(UserName,BeatMapName);
    }
	@Override
	public int AddBM(String UserName, int BMId) {
		return addbm(UserName,BMId);
	}

}
