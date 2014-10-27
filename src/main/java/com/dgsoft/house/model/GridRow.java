package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * GridRow generated by hbm2java
 */
@Entity
@Table(name = "GRID_ROW", catalog = "HOUSE_INFO")
public class GridRow implements java.io.Serializable {

	private String id;
	private BuildGridMap buildGridMap;
	private String title;
	private int order;
    private int floorIndex;
	private Set<GridBlock> gridBlocks = new HashSet<GridBlock>(0);

	public GridRow() {
	}

    public GridRow(BuildGridMap buildGridMap, String title, int floorIndex) {
        this.buildGridMap = buildGridMap;
        this.title = title;
        this.floorIndex = floorIndex;
    }

    public GridRow(BuildGridMap buildGridMap, String title, int order, int floorIndex) {
		this.buildGridMap = buildGridMap;
		this.title = title;
		this.order = order;
        this.floorIndex = floorIndex;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRID_ID", nullable = false)
	@NotNull
	public BuildGridMap getBuildGridMap() {
		return this.buildGridMap;
	}

	public void setBuildGridMap(BuildGridMap buildGridMap) {
		this.buildGridMap = buildGridMap;
	}

	@Column(name = "TITLE", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "_ORDER", nullable = false)
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

    @Column(name = "FLOOR_INDEX")
    @NotNull
    public int getFloorIndex() {
        return floorIndex;
    }

    public void setFloorIndex(int floorIndex) {
        this.floorIndex = floorIndex;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gridRow", orphanRemoval = true , cascade = {CascadeType.ALL})
	public Set<GridBlock> getGridBlocks() {
		return this.gridBlocks;
	}

	public void setGridBlocks(Set<GridBlock> gridBlocks) {
		this.gridBlocks = gridBlocks;
	}

    @Transient
    public List<GridBlock> getGridBlockList(){
        List<GridBlock> result = new ArrayList<GridBlock>(getGridBlocks());
        Collections.sort(result,new Comparator<GridBlock>() {
            @Override
            public int compare(GridBlock o1, GridBlock o2) {
                return new Integer(o1.getOrder()).compareTo(o2.getOrder());
            }
        });
        return result;
    }

}
