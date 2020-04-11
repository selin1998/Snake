package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeeApp  {
  private List<LPoint> obstacles=new ArrayList<>();
  private Point head;
  private Point apple;
  private int boardx;
  private int boardy;
  public LeeApp(Board board) {
    this.head=board.getHead();
    if(this.head!=null){
      boardx=head.getX();
      boardy=head.getY();
    }
    else{
      boardx=9;
      boardy=8;
    }
    this.apple=board.getApples().get(0);
    List<Point> barriers = board.getBarriers();
    barriers.stream().map(b -> new LPoint(b.getX(), b.getY())).forEach(lpoint->obstacles.add(lpoint));

  }

  public Direction solve(){
    int appleX=apple.getX();
    int appleY=apple.getY();

    LPoint to=LPoint.of(appleX,appleY);
    LPoint from=LPoint.of(boardx,boardy);
    Lee lee = new Lee(15, 15);
    Optional<List<LPoint>> path = lee.trace(from, to, obstacles);
    if (path.isPresent()) {
     // System.out.println(path);
      LPoint next_step = path.get().get(1);
      LPoint curr=from;
      if      (next_step.x < curr.x) return Direction.LEFT;
      else if (next_step.x > curr.x) return Direction.RIGHT;
      else if (next_step.y > curr.y) return Direction.UP;
      else if (next_step.y < curr.y) return Direction.DOWN;


    }
    //  System.out.println(path);
    return Direction.random();
  }


}
