class MessagesReceived < ActiveRecord::Base
  belongs_to :message, :class_name=>'Message'
  belongs_to :user, :class_name=>'User'
end
