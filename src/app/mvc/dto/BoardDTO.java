package app.mvc.dto;

import java.util.List;

public class BoardDTO {
	private int boardNo; // 글번호
	private String subject; // 제목
	private String writer; // 작성자
	private String content; // 내용
	private String boardDate; // 등록일
	
	//댓글정보
	private List<ReplyDTO> repliesList ;
	
	
	public BoardDTO() {}
	public BoardDTO(int boardNo, String subject, String writer, String content, String boardDate) {
		super();
		this.boardNo = boardNo;
		this.subject = subject;
		this.writer = writer;
		this.content = content;
		this.boardDate = boardDate;
	}

	public BoardDTO(int boardNo, String subject, String writer, String content, String boardDate,
			List<ReplyDTO> repliesList) {
		this.boardNo = boardNo;
		this.subject = subject;
		this.writer = writer;
		this.content = content;
		this.boardDate = boardDate;
		this.repliesList = repliesList;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	
	
	public List<ReplyDTO> getRepliesList() {
		return repliesList;
	}
	public void setRepliesList(List<ReplyDTO> repliesList) {
		this.repliesList = repliesList;
	}
	
	@Override
	public String toString() {
		return boardNo + " | " + writer + "|" + subject + "|" + "|" + content + "|" + boardDate;
	}
}
