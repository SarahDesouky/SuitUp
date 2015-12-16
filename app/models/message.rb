class Message < ActiveRecord::Base
  belongs_to :owner, :class_name=>'User'
  belongs_to :user, :class_name =>'User'
  validates :text, presence: true
  scope :unread, -> {where(read: false)}
  scope :read, -> {where(read: true)}
end
