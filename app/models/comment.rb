class Comment < ActiveRecord::Base
  belongs_to :owner, :class_name=>'User'
  belongs_to :post, :class_name=>'Post'
end
