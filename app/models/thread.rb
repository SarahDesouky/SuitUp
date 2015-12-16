class Thread < ActiveRecord::Base

	has_many :messages, :class_name => 'Message'
	
end
