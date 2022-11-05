package net.codejava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String tieude;
	
	@Column(nullable = false, length = 255)
	private String tacgia;
	
	@Column(columnDefinition = "text")
	private String mota;
	
	@Column(nullable = false)
	private String ngayphathanh;

	@Column(nullable = true )
	private int sotrang;
	
	@Column(nullable = false, length = 255)
	private String theloai;
	
	@Column(length = 255)
	private String anh;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTieude() {
		return tieude;
	}

	public void setTieude(String tieude) {
		this.tieude = tieude;
	}

	public String getTacgia() {
		return tacgia;
	}

	public void setTacgia(String tacgia) {
		this.tacgia = tacgia;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public String getNgayphathanh() {
		return ngayphathanh;
	}

	public void setNgayphathanh(String ngayphathanh) {
		this.ngayphathanh = ngayphathanh;
	}

	public int getSotrang() {
		return sotrang;
	}

	public void setSotrang(int sotrang) {
		this.sotrang = sotrang;
	}

	public String getTheloai() {
		return theloai;
	}

	public void setTheloai(String theloai) {
		this.theloai = theloai;
	}

	public String getAnh() {
		return anh;
	}

	public void setAnh(String anh) {
		this.anh = anh;
	}

	public Book() {
		super();
	}

	public Book(Long id, String tieude, String tacgia, String mota, String ngayphathanh, int sotrang, String theloai) {
		super();
		this.id = id;
		this.tieude = tieude;
		this.tacgia = tacgia;
		this.mota = mota;
		this.ngayphathanh = ngayphathanh;
		this.sotrang = sotrang;
		this.theloai = theloai;
	}
	
}
