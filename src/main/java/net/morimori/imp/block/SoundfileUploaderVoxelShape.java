package net.morimori.imp.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.morimori.imp.util.VoxelShapeHelper;

public class SoundfileUploaderVoxelShape {
	private static final VoxelShape NORTH_PART1 =  VoxelShapeHelper.makeCuboidShaoe270(0, 0.5, 3, 4, 12.5, 13);
	private static final VoxelShape NORTH_PART2 =  VoxelShapeHelper.makeCuboidShaoe270(6, 0, 3, 15, 0.5, 8);
	private static final VoxelShape NORTH_PART3 =  VoxelShapeHelper.makeCuboidShaoe270(9, 0, 10, 12, 0.5, 13);
	private static final VoxelShape NORTH_PART4 =  VoxelShapeHelper.makeCuboidShaoe270(10, 0.5, 11, 11, 3, 12);
	private static final VoxelShape NORTH_PART5 =  VoxelShapeHelper.makeCuboidShaoe270(5, 3, 11, 16, 10, 12);
	private static final VoxelShape NORTH_PART6 =  VoxelShapeHelper.makeCuboidShaoe270(0, 0, 3, 1, 0.5, 4);
	private static final VoxelShape NORTH_PART7 =  VoxelShapeHelper.makeCuboidShaoe270(3, 0, 3, 4, 0.5, 4);
	private static final VoxelShape NORTH_PART8 =  VoxelShapeHelper.makeCuboidShaoe270(3, 0, 12, 4, 0.5, 13);
	private static final VoxelShape NORTH_PART9 =  VoxelShapeHelper.makeCuboidShaoe270(0, 0, 12, 1, 0.5, 13);
	private static final VoxelShape NORTH_PART10 =  VoxelShapeHelper.makeCuboidShaoe270(1, 12.5, 7, 3, 13, 9);
	private static final VoxelShape NORTH_PART11 =  VoxelShapeHelper.makeCuboidShaoe270(2, 14, 8, 2.5, 14.5, 8.5);
	private static final VoxelShape NORTH_PART12 =  VoxelShapeHelper.makeCuboidShaoe270(1, 13, 7, 1.5, 13.5, 7.5);
	private static final VoxelShape NORTH_PART13 =  VoxelShapeHelper.makeCuboidShaoe270(1, 13.5, 8, 1.5, 14, 8.5);
	private static final VoxelShape NORTH_PART14 =  VoxelShapeHelper.makeCuboidShaoe270(2, 13.5, 7, 2.5, 14, 7.5);
	private static final VoxelShape NORTH_PART15 =  VoxelShapeHelper.makeCuboidShaoe270(1.5, 13.5, 7, 2, 14, 7.5);
	private static final VoxelShape NORTH_PART16 =  VoxelShapeHelper.makeCuboidShaoe270(2.5, 13.5, 8, 3, 14, 8.5);
	private static final VoxelShape NORTH_PART17 =  VoxelShapeHelper.makeCuboidShaoe270(1, 13, 8.5, 1.5, 13.5, 9);
	private static final VoxelShape NORTH_PART18 =  VoxelShapeHelper.makeCuboidShaoe270(2, 14, 7.5, 2.5, 14.5, 8);
	private static final VoxelShape NORTH_PART19 =  VoxelShapeHelper.makeCuboidShaoe270(2.5, 13, 8.5, 3, 13.5, 9);
	private static final VoxelShape NORTH_PART20 =  VoxelShapeHelper.makeCuboidShaoe270(1.5, 14, 7.5, 2, 14.5, 8);
	private static final VoxelShape NORTH_PART21 =  VoxelShapeHelper.makeCuboidShaoe270(2.5, 13.5, 7.5, 3, 14, 8);
	private static final VoxelShape NORTH_PART22 =  VoxelShapeHelper.makeCuboidShaoe270(2.5, 13.5, 7.5, 3, 14, 8);
	private static final VoxelShape NORTH_PART23 =  VoxelShapeHelper.makeCuboidShaoe270(1.5, 13.5, 8.5, 2, 14, 9);
	private static final VoxelShape NORTH_PART24 =  VoxelShapeHelper.makeCuboidShaoe270(1.5, 13.5, 8.5, 2, 14, 9);
	private static final VoxelShape NORTH_PART25 =  VoxelShapeHelper.makeCuboidShaoe270(1, 13.5, 7.5, 1.5, 14, 8);
	private static final VoxelShape NORTH_PART26 =  VoxelShapeHelper.makeCuboidShaoe270(2, 13.5, 8.5, 2.5, 14, 9);
	private static final VoxelShape NORTH_PART27 =  VoxelShapeHelper.makeCuboidShaoe270(1, 13.5, 7.5, 1.5, 14, 8);
	private static final VoxelShape NORTH_PART28 =  VoxelShapeHelper.makeCuboidShaoe270(2.5, 13, 7, 3, 13.5, 7.5);
	private static final VoxelShape NORTH_PART29 =  VoxelShapeHelper.makeCuboidShaoe270(1.5, 14, 8, 2, 14.5, 8.5);
	private static final VoxelShape NORTH_PART30 =  VoxelShapeHelper.makeCuboidShaoe270(1.75, 14.5, 7.75, 2.25, 15.5, 8.25);
	protected static final VoxelShape NORTH_AXIS_AABB = VoxelShapes.or(NORTH_PART1,NORTH_PART2,NORTH_PART3,NORTH_PART4,NORTH_PART5,NORTH_PART6,NORTH_PART7,NORTH_PART8,NORTH_PART9,NORTH_PART10,NORTH_PART11,NORTH_PART12,NORTH_PART13,NORTH_PART14,NORTH_PART15,NORTH_PART16,NORTH_PART17,NORTH_PART18,NORTH_PART19,NORTH_PART20,NORTH_PART21,NORTH_PART22,NORTH_PART23,NORTH_PART24,NORTH_PART25,NORTH_PART26,NORTH_PART27,NORTH_PART28,NORTH_PART29,NORTH_PART30);

	private static final VoxelShape WEST_PART1 =  VoxelShapeHelper.makeCuboidShaoe0(0, 0.5, 3, 4, 12.5, 13);
	private static final VoxelShape WEST_PART2 =  VoxelShapeHelper.makeCuboidShaoe0(6, 0, 3, 15, 0.5, 8);
	private static final VoxelShape WEST_PART3 =  VoxelShapeHelper.makeCuboidShaoe0(9, 0, 10, 12, 0.5, 13);
	private static final VoxelShape WEST_PART4 =  VoxelShapeHelper.makeCuboidShaoe0(10, 0.5, 11, 11, 3, 12);
	private static final VoxelShape WEST_PART5 =  VoxelShapeHelper.makeCuboidShaoe0(5, 3, 11, 16, 10, 12);
	private static final VoxelShape WEST_PART6 =  VoxelShapeHelper.makeCuboidShaoe0(0, 0, 3, 1, 0.5, 4);
	private static final VoxelShape WEST_PART7 =  VoxelShapeHelper.makeCuboidShaoe0(3, 0, 3, 4, 0.5, 4);
	private static final VoxelShape WEST_PART8 =  VoxelShapeHelper.makeCuboidShaoe0(3, 0, 12, 4, 0.5, 13);
	private static final VoxelShape WEST_PART9 =  VoxelShapeHelper.makeCuboidShaoe0(0, 0, 12, 1, 0.5, 13);
	private static final VoxelShape WEST_PART10 =  VoxelShapeHelper.makeCuboidShaoe0(1, 12.5, 7, 3, 13, 9);
	private static final VoxelShape WEST_PART11 =  VoxelShapeHelper.makeCuboidShaoe0(2, 14, 8, 2.5, 14.5, 8.5);
	private static final VoxelShape WEST_PART12 =  VoxelShapeHelper.makeCuboidShaoe0(1, 13, 7, 1.5, 13.5, 7.5);
	private static final VoxelShape WEST_PART13 =  VoxelShapeHelper.makeCuboidShaoe0(1, 13.5, 8, 1.5, 14, 8.5);
	private static final VoxelShape WEST_PART14 =  VoxelShapeHelper.makeCuboidShaoe0(2, 13.5, 7, 2.5, 14, 7.5);
	private static final VoxelShape WEST_PART15 =  VoxelShapeHelper.makeCuboidShaoe0(1.5, 13.5, 7, 2, 14, 7.5);
	private static final VoxelShape WEST_PART16 =  VoxelShapeHelper.makeCuboidShaoe0(2.5, 13.5, 8, 3, 14, 8.5);
	private static final VoxelShape WEST_PART17 =  VoxelShapeHelper.makeCuboidShaoe0(1, 13, 8.5, 1.5, 13.5, 9);
	private static final VoxelShape WEST_PART18 =  VoxelShapeHelper.makeCuboidShaoe0(2, 14, 7.5, 2.5, 14.5, 8);
	private static final VoxelShape WEST_PART19 =  VoxelShapeHelper.makeCuboidShaoe0(2.5, 13, 8.5, 3, 13.5, 9);
	private static final VoxelShape WEST_PART20 =  VoxelShapeHelper.makeCuboidShaoe0(1.5, 14, 7.5, 2, 14.5, 8);
	private static final VoxelShape WEST_PART21 =  VoxelShapeHelper.makeCuboidShaoe0(2.5, 13.5, 7.5, 3, 14, 8);
	private static final VoxelShape WEST_PART22 =  VoxelShapeHelper.makeCuboidShaoe0(2.5, 13.5, 7.5, 3, 14, 8);
	private static final VoxelShape WEST_PART23 =  VoxelShapeHelper.makeCuboidShaoe0(1.5, 13.5, 8.5, 2, 14, 9);
	private static final VoxelShape WEST_PART24 =  VoxelShapeHelper.makeCuboidShaoe0(1.5, 13.5, 8.5, 2, 14, 9);
	private static final VoxelShape WEST_PART25 =  VoxelShapeHelper.makeCuboidShaoe0(1, 13.5, 7.5, 1.5, 14, 8);
	private static final VoxelShape WEST_PART26 =  VoxelShapeHelper.makeCuboidShaoe0(2, 13.5, 8.5, 2.5, 14, 9);
	private static final VoxelShape WEST_PART27 =  VoxelShapeHelper.makeCuboidShaoe0(1, 13.5, 7.5, 1.5, 14, 8);
	private static final VoxelShape WEST_PART28 =  VoxelShapeHelper.makeCuboidShaoe0(2.5, 13, 7, 3, 13.5, 7.5);
	private static final VoxelShape WEST_PART29 =  VoxelShapeHelper.makeCuboidShaoe0(1.5, 14, 8, 2, 14.5, 8.5);
	private static final VoxelShape WEST_PART30 =  VoxelShapeHelper.makeCuboidShaoe0(1.75, 14.5, 7.75, 2.25, 15.5, 8.25);
	protected static final VoxelShape WEST_AXIS_AABB = VoxelShapes.or(WEST_PART1,WEST_PART2,WEST_PART3,WEST_PART4,WEST_PART5,WEST_PART6,WEST_PART7,WEST_PART8,WEST_PART9,WEST_PART10,WEST_PART11,WEST_PART12,WEST_PART13,WEST_PART14,WEST_PART15,WEST_PART16,WEST_PART17,WEST_PART18,WEST_PART19,WEST_PART20,WEST_PART21,WEST_PART22,WEST_PART23,WEST_PART24,WEST_PART25,WEST_PART26,WEST_PART27,WEST_PART28,WEST_PART29,WEST_PART30);

	private static final VoxelShape SOUTH_PART1 =  VoxelShapeHelper.makeCuboidShaoe90(0, 0.5, 3, 4, 12.5, 13);
	private static final VoxelShape SOUTH_PART2 =  VoxelShapeHelper.makeCuboidShaoe90(6, 0, 3, 15, 0.5, 8);
	private static final VoxelShape SOUTH_PART3 =  VoxelShapeHelper.makeCuboidShaoe90(9, 0, 10, 12, 0.5, 13);
	private static final VoxelShape SOUTH_PART4 =  VoxelShapeHelper.makeCuboidShaoe90(10, 0.5, 11, 11, 3, 12);
	private static final VoxelShape SOUTH_PART5 =  VoxelShapeHelper.makeCuboidShaoe90(5, 3, 11, 16, 10, 12);
	private static final VoxelShape SOUTH_PART6 =  VoxelShapeHelper.makeCuboidShaoe90(0, 0, 3, 1, 0.5, 4);
	private static final VoxelShape SOUTH_PART7 =  VoxelShapeHelper.makeCuboidShaoe90(3, 0, 3, 4, 0.5, 4);
	private static final VoxelShape SOUTH_PART8 =  VoxelShapeHelper.makeCuboidShaoe90(3, 0, 12, 4, 0.5, 13);
	private static final VoxelShape SOUTH_PART9 =  VoxelShapeHelper.makeCuboidShaoe90(0, 0, 12, 1, 0.5, 13);
	private static final VoxelShape SOUTH_PART10 =  VoxelShapeHelper.makeCuboidShaoe90(1, 12.5, 7, 3, 13, 9);
	private static final VoxelShape SOUTH_PART11 =  VoxelShapeHelper.makeCuboidShaoe90(2, 14, 8, 2.5, 14.5, 8.5);
	private static final VoxelShape SOUTH_PART12 =  VoxelShapeHelper.makeCuboidShaoe90(1, 13, 7, 1.5, 13.5, 7.5);
	private static final VoxelShape SOUTH_PART13 =  VoxelShapeHelper.makeCuboidShaoe90(1, 13.5, 8, 1.5, 14, 8.5);
	private static final VoxelShape SOUTH_PART14 =  VoxelShapeHelper.makeCuboidShaoe90(2, 13.5, 7, 2.5, 14, 7.5);
	private static final VoxelShape SOUTH_PART15 =  VoxelShapeHelper.makeCuboidShaoe90(1.5, 13.5, 7, 2, 14, 7.5);
	private static final VoxelShape SOUTH_PART16 =  VoxelShapeHelper.makeCuboidShaoe90(2.5, 13.5, 8, 3, 14, 8.5);
	private static final VoxelShape SOUTH_PART17 =  VoxelShapeHelper.makeCuboidShaoe90(1, 13, 8.5, 1.5, 13.5, 9);
	private static final VoxelShape SOUTH_PART18 =  VoxelShapeHelper.makeCuboidShaoe90(2, 14, 7.5, 2.5, 14.5, 8);
	private static final VoxelShape SOUTH_PART19 =  VoxelShapeHelper.makeCuboidShaoe90(2.5, 13, 8.5, 3, 13.5, 9);
	private static final VoxelShape SOUTH_PART20 =  VoxelShapeHelper.makeCuboidShaoe90(1.5, 14, 7.5, 2, 14.5, 8);
	private static final VoxelShape SOUTH_PART21 =  VoxelShapeHelper.makeCuboidShaoe90(2.5, 13.5, 7.5, 3, 14, 8);
	private static final VoxelShape SOUTH_PART22 =  VoxelShapeHelper.makeCuboidShaoe90(2.5, 13.5, 7.5, 3, 14, 8);
	private static final VoxelShape SOUTH_PART23 =  VoxelShapeHelper.makeCuboidShaoe90(1.5, 13.5, 8.5, 2, 14, 9);
	private static final VoxelShape SOUTH_PART24 =  VoxelShapeHelper.makeCuboidShaoe90(1.5, 13.5, 8.5, 2, 14, 9);
	private static final VoxelShape SOUTH_PART25 =  VoxelShapeHelper.makeCuboidShaoe90(1, 13.5, 7.5, 1.5, 14, 8);
	private static final VoxelShape SOUTH_PART26 =  VoxelShapeHelper.makeCuboidShaoe90(2, 13.5, 8.5, 2.5, 14, 9);
	private static final VoxelShape SOUTH_PART27 =  VoxelShapeHelper.makeCuboidShaoe90(1, 13.5, 7.5, 1.5, 14, 8);
	private static final VoxelShape SOUTH_PART28 =  VoxelShapeHelper.makeCuboidShaoe90(2.5, 13, 7, 3, 13.5, 7.5);
	private static final VoxelShape SOUTH_PART29 =  VoxelShapeHelper.makeCuboidShaoe90(1.5, 14, 8, 2, 14.5, 8.5);
	private static final VoxelShape SOUTH_PART30 =  VoxelShapeHelper.makeCuboidShaoe90(1.75, 14.5, 7.75, 2.25, 15.5, 8.25);
	protected static final VoxelShape SOUTH_AXIS_AABB = VoxelShapes.or(SOUTH_PART1,SOUTH_PART2,SOUTH_PART3,SOUTH_PART4,SOUTH_PART5,SOUTH_PART6,SOUTH_PART7,SOUTH_PART8,SOUTH_PART9,SOUTH_PART10,SOUTH_PART11,SOUTH_PART12,SOUTH_PART13,SOUTH_PART14,SOUTH_PART15,SOUTH_PART16,SOUTH_PART17,SOUTH_PART18,SOUTH_PART19,SOUTH_PART20,SOUTH_PART21,SOUTH_PART22,SOUTH_PART23,SOUTH_PART24,SOUTH_PART25,SOUTH_PART26,SOUTH_PART27,SOUTH_PART28,SOUTH_PART29,SOUTH_PART30);

	private static final VoxelShape EAST_PART1 =  VoxelShapeHelper.makeCuboidShaoe180(0, 0.5, 3, 4, 12.5, 13);
	private static final VoxelShape EAST_PART2 =  VoxelShapeHelper.makeCuboidShaoe180(6, 0, 3, 15, 0.5, 8);
	private static final VoxelShape EAST_PART3 =  VoxelShapeHelper.makeCuboidShaoe180(9, 0, 10, 12, 0.5, 13);
	private static final VoxelShape EAST_PART4 =  VoxelShapeHelper.makeCuboidShaoe180(10, 0.5, 11, 11, 3, 12);
	private static final VoxelShape EAST_PART5 =  VoxelShapeHelper.makeCuboidShaoe180(5, 3, 11, 16, 10, 12);
	private static final VoxelShape EAST_PART6 =  VoxelShapeHelper.makeCuboidShaoe180(0, 0, 3, 1, 0.5, 4);
	private static final VoxelShape EAST_PART7 =  VoxelShapeHelper.makeCuboidShaoe180(3, 0, 3, 4, 0.5, 4);
	private static final VoxelShape EAST_PART8 =  VoxelShapeHelper.makeCuboidShaoe180(3, 0, 12, 4, 0.5, 13);
	private static final VoxelShape EAST_PART9 =  VoxelShapeHelper.makeCuboidShaoe180(0, 0, 12, 1, 0.5, 13);
	private static final VoxelShape EAST_PART10 =  VoxelShapeHelper.makeCuboidShaoe180(1, 12.5, 7, 3, 13, 9);
	private static final VoxelShape EAST_PART11 =  VoxelShapeHelper.makeCuboidShaoe180(2, 14, 8, 2.5, 14.5, 8.5);
	private static final VoxelShape EAST_PART12 =  VoxelShapeHelper.makeCuboidShaoe180(1, 13, 7, 1.5, 13.5, 7.5);
	private static final VoxelShape EAST_PART13 =  VoxelShapeHelper.makeCuboidShaoe180(1, 13.5, 8, 1.5, 14, 8.5);
	private static final VoxelShape EAST_PART14 =  VoxelShapeHelper.makeCuboidShaoe180(2, 13.5, 7, 2.5, 14, 7.5);
	private static final VoxelShape EAST_PART15 =  VoxelShapeHelper.makeCuboidShaoe180(1.5, 13.5, 7, 2, 14, 7.5);
	private static final VoxelShape EAST_PART16 =  VoxelShapeHelper.makeCuboidShaoe180(2.5, 13.5, 8, 3, 14, 8.5);
	private static final VoxelShape EAST_PART17 =  VoxelShapeHelper.makeCuboidShaoe180(1, 13, 8.5, 1.5, 13.5, 9);
	private static final VoxelShape EAST_PART18 =  VoxelShapeHelper.makeCuboidShaoe180(2, 14, 7.5, 2.5, 14.5, 8);
	private static final VoxelShape EAST_PART19 =  VoxelShapeHelper.makeCuboidShaoe180(2.5, 13, 8.5, 3, 13.5, 9);
	private static final VoxelShape EAST_PART20 =  VoxelShapeHelper.makeCuboidShaoe180(1.5, 14, 7.5, 2, 14.5, 8);
	private static final VoxelShape EAST_PART21 =  VoxelShapeHelper.makeCuboidShaoe180(2.5, 13.5, 7.5, 3, 14, 8);
	private static final VoxelShape EAST_PART22 =  VoxelShapeHelper.makeCuboidShaoe180(2.5, 13.5, 7.5, 3, 14, 8);
	private static final VoxelShape EAST_PART23 =  VoxelShapeHelper.makeCuboidShaoe180(1.5, 13.5, 8.5, 2, 14, 9);
	private static final VoxelShape EAST_PART24 =  VoxelShapeHelper.makeCuboidShaoe180(1.5, 13.5, 8.5, 2, 14, 9);
	private static final VoxelShape EAST_PART25 =  VoxelShapeHelper.makeCuboidShaoe180(1, 13.5, 7.5, 1.5, 14, 8);
	private static final VoxelShape EAST_PART26 =  VoxelShapeHelper.makeCuboidShaoe180(2, 13.5, 8.5, 2.5, 14, 9);
	private static final VoxelShape EAST_PART27 =  VoxelShapeHelper.makeCuboidShaoe180(1, 13.5, 7.5, 1.5, 14, 8);
	private static final VoxelShape EAST_PART28 =  VoxelShapeHelper.makeCuboidShaoe180(2.5, 13, 7, 3, 13.5, 7.5);
	private static final VoxelShape EAST_PART29 =  VoxelShapeHelper.makeCuboidShaoe180(1.5, 14, 8, 2, 14.5, 8.5);
	private static final VoxelShape EAST_PART30 =  VoxelShapeHelper.makeCuboidShaoe180(1.75, 14.5, 7.75, 2.25, 15.5, 8.25);
	protected static final VoxelShape EAST_AXIS_AABB = VoxelShapes.or(EAST_PART1,EAST_PART2,EAST_PART3,EAST_PART4,EAST_PART5,EAST_PART6,EAST_PART7,EAST_PART8,EAST_PART9,EAST_PART10,EAST_PART11,EAST_PART12,EAST_PART13,EAST_PART14,EAST_PART15,EAST_PART16,EAST_PART17,EAST_PART18,EAST_PART19,EAST_PART20,EAST_PART21,EAST_PART22,EAST_PART23,EAST_PART24,EAST_PART25,EAST_PART26,EAST_PART27,EAST_PART28,EAST_PART29,EAST_PART30);


}