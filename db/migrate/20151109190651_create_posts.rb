class CreatePosts < ActiveRecord::Migration
  def change
    create_table :posts do |t|
      t.references :owner, index: true
      t.string :text
      t.string :image_url
      t.references :profile, index: true

      t.timestamps
    end
  end
end
