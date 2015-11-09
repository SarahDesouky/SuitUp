class CreateFollowships < ActiveRecord::Migration
  def change
    create_table :followships do |t|
      t.references :follower, index: true
      t.references :followee, index: true

      t.timestamps
    end
  end
end
