package visualization.tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;

import org.eclipse.sphinx.emf.resource.ContextAwareProxyURIHelper;

public class Visualizer {
	
	final float monospaced_font_ratio = 3f / 5f ;
	final int task_font_size = 25;
	final int scale_font_size = 20;
	final int measurement_font_size = 15;
	final int grid_line_height = 50;
	final int grid_line_width = 10;
	final int dash_index = 9;
	final int arrow_width = 5;
	final int arrow_height = 5;
	final int arrow_line_width = 3;
	final int line_width = 1;
	final float time_ratio = 100;
	final int number_offset = scale_font_size;
	final int space_after_name_offset = 20;
	final int scale_step = 100;
	final int scale_line_size = 3;
	final int scale_line_width = 3;
	final int chain_line_width = 2;
	final int measurement_offset = 30;
	final int measurement_line_width = 2;
	final String unit = "ms";
	final Color task_color = Color.GREEN;
	final Color measurement_color = Color.RED;
	private ArrayList<Event> event_queue;
	private ArrayList<Task> tasks;
	private ArrayList<Event_chain> event_chains;
	private float max_time;
	private int name_offset;
	private int grid_height;
	private int grid_width;
	private int image_height;
	private int image_width;
	
	public void initialize(ArrayList<Event> event_queue, ArrayList<Task> tasks, ArrayList<Event_chain> event_chains, float max_time) {
		this.event_queue = event_queue;
		this.tasks = tasks;
		this.event_chains = event_chains;
		this.max_time = max_time;
		
		name_offset = 0;
		for (Task task : tasks) {
			int name_width = (int)(task.get_name().length() * task_font_size * monospaced_font_ratio);
			if (name_offset < name_width) {
				name_offset = name_width;
			}
		}
		name_offset += space_after_name_offset;
		
		grid_height = tasks.size() * grid_line_height;
		grid_width = (int)(max_time * time_ratio);
		
		image_height = grid_height + number_offset + measurement_offset;
		image_width = grid_width + name_offset;
	}
	
	public BufferedImage draw_event_queue(Event_chain event_chain, boolean runnable_level) {
		
		BufferedImage image = new BufferedImage(image_width, image_height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = image.createGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		draw_grid(graphics, name_offset, 0, grid_width, grid_height);
		
		for (int i = 0; i < tasks.size(); i++) {
			print_string(graphics, 0, grid_line_height * (1 + i) - (grid_line_height / 2) + (task_font_size / 2), tasks.get(i).get_name(), task_font_size, Color.BLACK);
		}
		
		for (Event event : event_queue) {
			if (event.get_type() == Event_type.TASK_START) {
				Task task = event.get_task();
				int x = name_offset + (int)(event.get_time() * time_ratio);
				int y = (tasks.indexOf(task) + 1) * grid_line_height;
				int width = (int)(task.get_total_time() * time_ratio);
				draw_task(graphics, x, y, width);
			}
		}
		
		for (Event event : event_queue) {
			if (event.get_type() == Event_type.TASK_QUEUED) {
				Task task = event.get_task();
				int x = name_offset + (int)(event.get_time() * time_ratio);
				int y = (tasks.indexOf(task) + 1) * grid_line_height;
				draw_enqueue_arrow(graphics, x, y);
			}
		}
		
		for (Event start_event : event_queue) {
			if (start_event.get_type() == Event_type.CHAIN_START) {
				if (start_event.get_event_chain() == event_chain) {
					Event end_event = get_event_chain_end(start_event);
					if (end_event != null) {
						if (runnable_level == true) {
							draw_chain_event(graphics, start_event, end_event);
						}
						else {
							Event task_start_event = null;
							for (Event event : event_queue) {
								if (event.get_type() == Event_type.TASK_START) {
									if (event.get_task() == start_event.get_task()) {
										if (event.get_time() <= start_event.get_time()) {
											if (task_start_event == null) {
												task_start_event = event;
											}
											else {
												if (event.get_time() > task_start_event.get_time()) {
													task_start_event = event;
												}
											}
										}
									}
								}
							}
							
							Event task_end_event = null;
							for (Event event : event_queue) {
								if (event.get_task() == end_event.get_task()) {
									if (event.get_type() == Event_type.TASK_END) {
										if (event.get_time() >= end_event.get_time()) {
											if (task_end_event == null) {
												task_end_event = event;
											}
											else {
												if (event.get_time() < task_end_event.get_time()) {
													task_end_event = event;
												}
											}
										}
									}
								}
							}
							
							draw_chain_event(graphics, task_start_event, task_end_event);
						}
					}
				}
			}
		}
		
		/*draw_grid(graphics, 120, 0, 500, 500);
		draw_task(graphics, 200, 200, 100);
		draw_enqueue_arrow(graphics, 200, 200);
		print_string(graphics, 10, 200, "Task 1");*/
		
		graphics.dispose();
		return image;
	}
	
	private Event get_event_chain_end(Event start_event) {
		Event end_event = null;
		
		for (Event event : event_queue) {
			if (event.get_type() == Event_type.CHAIN_END) {
				if (event.get_event_chain() == start_event.get_event_chain()) {
					if (event.get_time() > start_event.get_time()) {
						if (end_event == null) {
							end_event = event;
						}
						else {
							if (event.get_time() < event.get_time()) {
								end_event = event;
							}
						}
					}
				}
			}
		}
		
		return end_event;
	}
	
	private void draw_chain_event(Graphics2D graphics, Event chain_start, Event chain_end) {
		draw_chain_event_line(graphics, chain_start);
		draw_chain_event_line(graphics, chain_end);
		
		Task task_start = chain_start.get_task();
		int x_start = name_offset + (int)(chain_start.get_time() * time_ratio);
		int y_start = (tasks.indexOf(task_start) + 1) * grid_line_height - (grid_line_height / 2);
		
		Task task_end = chain_end.get_task();
		int x_end = name_offset + (int)(chain_end.get_time() * time_ratio);
		int y_end = (tasks.indexOf(task_end) + 1) * grid_line_height - (grid_line_height / 2);
		
		graphics.setColor(Color.BLACK);
		graphics.setStroke(new BasicStroke(chain_line_width));
		graphics.drawLine(x_start, y_start, x_end, y_end);
		
		int x_measurement_start = name_offset + (int)(chain_start.get_time() * time_ratio);
		int y_measurement_start = (tasks.indexOf(task_start) + 1) * grid_line_height;
		
		graphics.setColor(measurement_color);
		graphics.setStroke(new BasicStroke(measurement_line_width));
		graphics.drawLine(x_measurement_start, y_measurement_start, x_measurement_start, grid_height);
		graphics.drawLine(x_measurement_start, grid_height + number_offset + (measurement_offset / 2), x_measurement_start, grid_height + number_offset + measurement_offset);
		
		int x_measurement_end = name_offset + (int)(chain_end.get_time() * time_ratio);
		int y_measurement_end = (tasks.indexOf(task_end) + 1) * grid_line_height;
		
		graphics.setColor(measurement_color);
		graphics.setStroke(new BasicStroke(measurement_line_width));
		graphics.drawLine(x_measurement_end, y_measurement_end, x_measurement_end, grid_height);
		graphics.drawLine(x_measurement_end, grid_height + number_offset + (measurement_offset / 2), x_measurement_end, grid_height + number_offset + measurement_offset);
		
		graphics.setColor(measurement_color);
		graphics.setStroke(new BasicStroke(measurement_line_width));
		graphics.drawLine(x_measurement_start, grid_height + number_offset + (int)(measurement_offset * 0.75), x_measurement_end, grid_height + number_offset + (int)(measurement_offset * 0.75));
		
		float time_difference = (x_measurement_end - x_measurement_start) / time_ratio;
		String time_difference_string = String.valueOf(time_difference);
		
		int x_measurement_string = (int)(x_measurement_start + ((x_measurement_end - x_measurement_start) / 2) - (time_difference_string.length() * measurement_font_size * monospaced_font_ratio / 2f));
		int y_measurement_string = grid_height + number_offset + (measurement_offset / 2);
		print_string(graphics, x_measurement_string, y_measurement_string, time_difference_string, measurement_font_size, measurement_color);
	}
	
	private void draw_chain_event_line(Graphics2D graphics, Event chain_event) {
		Task task = chain_event.get_task();
		int x = name_offset + (int)(chain_event.get_time() * time_ratio);
		int y = (tasks.indexOf(task) + 1) * grid_line_height;
		
		graphics.setColor(Color.BLACK);
		graphics.setStroke(new BasicStroke(chain_line_width));
		graphics.drawLine(x, y, x, y - grid_line_height);
	}
	
	private void draw_enqueue_arrow(Graphics2D graphics, int x, int y) {
		graphics.setColor(Color.BLACK);
		graphics.setStroke(new BasicStroke(arrow_line_width));
		graphics.drawLine(x, y, x, y - grid_line_height);
		graphics.drawLine(x, y, x - arrow_width, y - arrow_height);
		graphics.drawLine(x, y, x + arrow_width, y - arrow_height);
	}
	
	private void draw_task(Graphics2D graphics, int x, int y, int width) {
		graphics.setColor(task_color);
		graphics.setStroke(new BasicStroke(line_width));
		graphics.fillRect(x, y - grid_line_height, width, grid_line_height);
	}
	
	private void print_string(Graphics2D graphics, int x, int y, String s, int font_size, Color color) {
		graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, font_size));
		graphics.setColor(color);
		graphics.drawString(s, x, y);
	}
	
	private void draw_grid(Graphics2D graphics, int x_start, int y_start, int width, int height) {
		graphics.setColor(Color.BLACK);
		
		graphics.setStroke(new BasicStroke(line_width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{dash_index}, 0));
		for (int x = x_start; x <= x_start + width; x += grid_line_width) {
			graphics.drawLine(x, y_start, x, y_start + height);
		}
		
		graphics.setStroke(new BasicStroke(line_width));
		for (int y = y_start; y <= y_start + height; y += grid_line_height) {
			graphics.drawLine(x_start, y, x_start + width, y);
		}
		
		float scale_value = 0.0f;
		for (int x = x_start; x <= x_start + width; x += scale_step) {
			
			String s = String.valueOf(scale_value);
			print_string(graphics, x - (int)((s.length() * scale_font_size * monospaced_font_ratio) / 2), y_start + height + number_offset, s, scale_font_size, Color.BLACK);
			
			graphics.setColor(Color.BLACK);
			graphics.setStroke(new BasicStroke(scale_line_width));
			graphics.drawLine(x, height + scale_line_size, x, height - scale_line_size);
			
			scale_value += scale_step / time_ratio;
		}
		
		print_string(graphics, x_start - (int)(scale_font_size * monospaced_font_ratio * (unit.length() + 3)), y_start + height + number_offset, unit, scale_font_size, Color.BLACK);
	}
}
