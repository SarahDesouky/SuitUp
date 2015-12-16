class MessageThread < ActiveRecord::Base
	has_many :messages, :class_name=>'Message', :foreign_key=>'thread_id'
	belongs_to :user, :class_name=> 'User', :foreign_key=>'receiver_id'
end
