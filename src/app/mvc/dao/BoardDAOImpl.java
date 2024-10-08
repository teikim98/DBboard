package app.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.mvc.common.DBManager;
import app.mvc.dto.BoardDTO;
import app.mvc.dto.ReplyDTO;
import app.mvc.exception.DMLException;
import app.mvc.exception.SearchWrongException;

public class BoardDAOImpl implements BoardDAO {
	
	private static BoardDAO instance = new BoardDAOImpl();
	
	private BoardDAOImpl() {}
	
	public static BoardDAO getInstance() {
		return instance;
	}
	

	@Override
	public List<BoardDTO> boardSelectAll() throws SearchWrongException {
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		List<BoardDTO> list = new ArrayList<>();
		String sql="select * from board order by board_no desc";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()){
				BoardDTO board = new BoardDTO(
						rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5)
				);
				list.add(board);
			}

		}catch (SQLException e) {
			e.printStackTrace();
			throw new SearchWrongException("DB에 문제가 있어 다시 진행해주세요^^");
			
		}finally {
			DBManager.releaseConnection(con, ps, rs);
		}
		return list;
	}

	@Override
	public List<BoardDTO> boardSelectBySubject(String keyWord) throws SearchWrongException {
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		List<BoardDTO> list = new ArrayList<>();
		String sql = "select * from board where subject like ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + keyWord + "%");
			rs = ps.executeQuery();

			while (rs.next()){
				BoardDTO board = new BoardDTO(
						rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5)
				);
				list.add(board);
			}
		}catch (SQLException e){
			e.printStackTrace();
			throw new SearchWrongException("DB에 문제가 있어 다시 진행해주세요^^");
		}finally {
			DBManager.releaseConnection(con,ps,rs);
		}
		return list;
	}

	@Override
	public BoardDTO boardSelectByNo(int boardNo) throws SearchWrongException {
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		String sql = "select * from board where board_no = ? ";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);
			rs = ps.executeQuery();

			while (rs.next()){
				BoardDTO board = new BoardDTO(
						rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5)
				);
				if (board != null) {
					return board;
				}
			}
		}catch (SQLException e){
			e.printStackTrace();
			throw new SearchWrongException("DB에 문제가 있어 다시 진행해주세요^^");
		}finally {
			DBManager.releaseConnection(con,ps,rs);
		}
		return null;
	}

	@Override
	public int boardInsert(BoardDTO boardDTO) throws DMLException {
		Connection con=null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = " insert into board (board_no, subject, writer, content, board_date) values (board_seq.nextval, ?, ?, ?, sysdate)";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, boardDTO.getSubject());
			ps.setString(2, boardDTO.getWriter());
			ps.setString(3, boardDTO.getContent());
			result = ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new DMLException("DB에 문제가 있어 다시 진행해주세요^^");
		}finally {
			DBManager.releaseConnection(con,ps);
		}
		return result;
  }

	@Override
	public int boardUpdate(BoardDTO boardDTO) throws DMLException {
		Connection con=null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "update board set content = ? where board_no = ?";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, boardDTO.getContent());
			ps.setInt(2, boardDTO.getBoardNo());
			result = ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new DMLException("DB에 문제가 있어 다시 진행해주세요^^");
		}finally {
			DBManager.releaseConnection(con,ps);
		}
		return result;
	}

	@Override
	public int boardDelete(int boardNo) throws DMLException {
		Connection con=null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "delete from board where board_no = ?";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);
			result = ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new DMLException("DB에 문제가 있어 다시 진행해주세요^^");
		}finally {
			DBManager.releaseConnection(con,ps);
		}
		return result;
	}

	@Override
	public int replyInsert(ReplyDTO replyDTO) throws DMLException {
		Connection con=null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "insert into reply values(reply_no_seq.nextval , ?, ? , sysdate)";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, replyDTO.getReplyContent());
			ps.setInt(2, replyDTO.getBoardNo());
			result = ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new DMLException("DB에 문제가 있어 다시 진행해주세요^^");
		}finally {
			DBManager.releaseConnection(con,ps);
		}
		return result;
	}

	@Override
	public BoardDTO replySelectByParentNo(int boardNo) throws SearchWrongException {
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		String sql = "select * from board join reply using(board_no) where board_no=?";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);
			rs = ps.executeQuery();
			List<ReplyDTO> list = new ArrayList<>();
			BoardDTO board = null;

			while (rs.next()){
				ReplyDTO reply = new ReplyDTO(
						rs.getInt(6),
						rs.getString(7),
						rs.getInt(1),
						rs.getString(8)
				);
				list.add(reply);
					board = new BoardDTO(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							list
						 	);
			}
			return board;
		}catch (SQLException e){
			e.printStackTrace();
			throw new SearchWrongException("DB에 문제가 있어 다시 진행해주세요^^");
		}finally {
			DBManager.releaseConnection(con,ps,rs);
		}
	}
	
	
	/***
	 * 부모글에 해당하는 댓글정보 가져오기
	 * */
	private List<ReplyDTO> replySelect(Connection con ,int boardNo)throws SQLException{
		//위에서 다 만들어 부렀으요
		return null;
	}

}













