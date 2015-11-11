class Post < ActiveRecord::Base
  belongs_to :owner, :class_name=>'User'
  belongs_to :profile, :class_name=>'User'
  has_many :comments, :class_name=>'Comment', :foreign_key=>'post_id'
  validates :text, presence: true
end
