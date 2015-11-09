class User < ActiveRecord::Base
	has_many :followers, :class_name=>'Followship', :foreing_key=> 'followee_id'
	has_many :followees, :class_name=>'Followship', :foreing_key=> 'follower_id'
	has_many :posts_on_friends_walls, :class_name=>'Post', :foreing_key=>'owner_id'
	has_many :posts_on_own_wall, :class_name=>'Post', :foreing_key=>'profile_id'
	has_many :comments, :class_name=>'Comment', :foreing_key=>'owner_id'
	has_many :messages_sent, :class_name=>'Message', :foreing_key=>'owner_id'
	has_many :messages_received, :class_name=.=>"Message", :foreing_key=>'user_id'

end
 