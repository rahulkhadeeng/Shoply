INSERT INTO categories (id,name,slug,icon,item_count) VALUES
 ('00000000-0000-0000-0000-000000000001','Electronics','electronics','Laptop',4203),
 ('00000000-0000-0000-0000-000000000002','Clothing','clothing','Shirt',8924),
 ('00000000-0000-0000-0000-000000000003','Home & Kitchen','home-kitchen','House',3110),
 ('00000000-0000-0000-0000-000000000004','Sports','sports','Dumbbell',1550),
 ('00000000-0000-0000-0000-000000000005','Books','books','BookOpen',2448);
INSERT INTO products (id,category_id,name,slug,description,price,previous_price,rating,image_url,featured) VALUES
 ('10000000-0000-0000-0000-000000000001','00000000-0000-0000-0000-000000000001','Pro Audio Studio Headphones','pro-audio-studio-headphones','Immersive sound for focused work and inspired listening.',199.00,249.00,4.8,'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=900&q=80',true),
 ('10000000-0000-0000-0000-000000000002','00000000-0000-0000-0000-000000000001','Minimalist Mechanical Keyboard','minimalist-mechanical-keyboard','A refined tactile keyboard for your everyday desk.',129.00,NULL,4.6,'https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=900&q=80',true),
 ('10000000-0000-0000-0000-000000000003','00000000-0000-0000-0000-000000000003','Ergonomic Mesh Chair','ergonomic-mesh-chair','Breathable support designed for long creative sessions.',349.00,399.00,4.5,'https://images.unsplash.com/photo-1505843490538-5133c6c6d0e1?auto=format&fit=crop&w=900&q=80',true),
 ('10000000-0000-0000-0000-000000000004','00000000-0000-0000-0000-000000000004','Eco Smart Water Bottle','eco-smart-water-bottle','Hydration reminders in a clean, responsible package.',45.00,NULL,4.9,'https://images.unsplash.com/photo-1602143407151-7111542de6e8?auto=format&fit=crop&w=900&q=80',true);
