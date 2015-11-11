class Message < ActiveRecord::Base
  belongs_to :owner, :class_name=>'User'
  belongs_to :thread, :class_name=>'Thread'
  has_many :messages_receivers, :class_name=>'MessageReceived', :foreign_key=>'message_id'
  validates :text, presence: true
  scope :unread, -> {where(read: false)}
  scope :read, -> {where(read: true)}
end
