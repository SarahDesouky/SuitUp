class User < ActiveRecord::Base
	has_many :followers, :class_name=>'Followship', :foreign_key=> 'followee_id'
	has_many :followees, :class_name=>'Followship', :foreign_key=> 'follower_id'
	has_many :posts_on_friends_walls, :class_name =>'Post', :foreign_key => 'owner_id'
	has_many :posts_on_own_wall, :class_name =>'Post', :foreign_key => 'profile_id'
	has_many :comments, :class_name =>'Comment', :foreign_key=>'owner_id'
	has_many :messages_sent, :class_name=>'Message', :foreign_key=>'owner_id'
	has_many :messages_received, :class_name=>'MessagesReceived', :foreign_key=>'user_id'
	validates :email, presence: true, uniqueness: true, format: {with: /\A([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})\z/i}
	# validates :password, presence: true, length: {in: 5..8}
	# validates :date_of_birth, presence:true, numericality: {greater_than_or_equal_to: 12}
	# validate :not_born_in_future
	
	# def not_born_in_future
	# 	self.errors.add :date_of_birth, 'is in the future' \
	# 	unless self.date_of_birth <= Date.today
	# end

end