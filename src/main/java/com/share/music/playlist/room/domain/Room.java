package com.share.music.playlist.room.domain;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ROOMS")
public class Room {

  @Id
  @GeneratedValue
  @Column(name = "ROOM_ID")
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "ROOM_MASTER_ID")
  private String ownerId;

  @OneToMany(mappedBy = "room")
  private List<Entrance> entrances = new ArrayList<>();

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CategoryTag> categoryTags = new HashSet<>();

  @Column(name = "NUMBER_LIMIT")
  @Min(1)
  private Integer limit;

  @Column(name = "ROOM_TITLE")
  @NotBlank
  private String title;

  @Column(name = "ROOM_COMMENT")
  private String comment;

  @Builder
  public Room(Long id, String ownerId, Integer limit, String title, String comment) {
    limit = Objects.requireNonNullElse(limit, 1);

    checkArgument(StringUtils.isNotBlank(ownerId), "ownerId must be provided.");
    checkArgument(StringUtils.isNotBlank(title), "title must be provided.");
    checkArgument(limit > 0, "limit must be positive.");

    this.id = id;
    this.ownerId = ownerId;
    this.limit = limit;
    this.title = title;
    this.comment = comment;

  }

  public void changeTitle(String title) {
    checkArgument(StringUtils.isNotBlank(title), "title must be provided.");
    this.title = title;
  }

  public void changeLimit(int limit) {
    checkArgument(limit > 0, "limit must be positive.");
    this.limit = limit;
  }

  public void changeComment(String comment) {
    checkArgument(Objects.nonNull(comment), "comment must be provided.");
    this.comment = comment;
  }
}
